package com.anthropic.claude.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * ViewModel for the background Tasks UI.
 *
 * Tasks are long-running agent jobs that execute asynchronously.
 * The user can view task status, steps, and results from this screen.
 */
class TasksViewModel(
    private val apiClient: AnthropicApiClient,
    private val orgId: String,
) : ViewModel() {

    private val _state = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val state: StateFlow<TasksUiState> = _state.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _state.value = TasksUiState.Loading
            try {
                val response = apiClient.listTasks(orgId)
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: "[]"
                    val tasks = kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }.decodeFromString<List<TaskItem>>(body)

                    _state.value = if (tasks.isEmpty()) {
                        TasksUiState.Empty
                    } else {
                        TasksUiState.Loaded(tasks)
                    }
                } else {
                    _state.value = TasksUiState.Error("Failed to load tasks: ${response.code}")
                }
            } catch (e: Exception) {
                _state.value = TasksUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun cancelTask(taskId: String) {
        viewModelScope.launch {
            try {
                apiClient.cancelTask(orgId, taskId)
                loadTasks()
            } catch (e: Exception) {
                // Silently handle — reload will show updated status
                loadTasks()
            }
        }
    }
}

// ── UI State ─────────────────────────────────────────────────────────────

sealed interface TasksUiState {
    data object Loading : TasksUiState
    data class Loaded(val tasks: List<TaskItem>) : TasksUiState
    data object Empty : TasksUiState
    data class Error(val message: String) : TasksUiState
}

@Serializable
data class TaskItem(
    val id: String,
    val title: String? = null,
    val status: TaskStatus = TaskStatus.PENDING,
    val steps: List<TaskStep> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val conversationId: String? = null,
)

@Serializable
enum class TaskStatus {
    PENDING,
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELLED,
}

@Serializable
data class TaskStep(
    val id: String? = null,
    val description: String,
    val status: TaskStatus = TaskStatus.PENDING,
    val output: String? = null,
)
