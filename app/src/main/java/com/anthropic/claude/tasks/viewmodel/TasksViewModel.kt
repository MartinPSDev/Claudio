package com.anthropic.claude.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.tasks.ApproveTaskRequest
import com.anthropic.claude.api.tasks.TaskModels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the tasks screen. */
data class TasksUiState(
    val tasks: List<Any> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTaskId: String? = null,
)

/**
 * ViewModel for the tasks / agent runs screen.
 * Manages task listing, approval flows, and polling.
 */
class TasksViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getTasks()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun selectTask(taskId: String) {
        _uiState.value = _uiState.value.copy(selectedTaskId = taskId)
    }

    fun approveTask(taskId: String, request: ApproveTaskRequest) {
        viewModelScope.launch {
            // TODO: apiClient.approveTask(taskId, request)
            loadTasks()
        }
    }

    fun cancelTask(taskId: String) {
        viewModelScope.launch {
            // TODO: apiClient.cancelTask(taskId)
            loadTasks()
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
