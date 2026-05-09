package com.anthropic.claude.tasks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.tasks.ApproveTaskRequest
import com.anthropic.claude.api.tasks.PaginatedTasksResponse
import com.anthropic.claude.api.tasks.TaskResponse
import com.anthropic.claude.api.tasks.TaskStatus
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

/** UI state for the tasks screen. */
data class TasksUiState(
    val tasks: List<TaskResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTaskId: String? = null,
    val hasMore: Boolean = false,
    val nextCursor: String? = null,
)

/**
 * ViewModel for the tasks / agent runs screen.
 * Manages task listing, approval flows, and polling.
 */
class TasksViewModel(
    private val apiClient: AnthropicApiClient,
    private val orgId: String,
) : ViewModel() {

    companion object {
        private const val TAG = "TasksViewModel"
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = apiClient.getTasks(orgId)
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: "{}"
                    val parsed = json.decodeFromString<PaginatedTasksResponse>(body)
                    _uiState.value = _uiState.value.copy(
                        tasks = parsed.tasks ?: emptyList(),
                        hasMore = parsed.pagination?.hasMore ?: false,
                        nextCursor = parsed.pagination?.nextCursor,
                        isLoading = false,
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load tasks (${response.code})",
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading tasks", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error loading tasks",
                )
            }
        }
    }

    fun loadMore() {
        val cursor = _uiState.value.nextCursor ?: return
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = apiClient.getTasks(orgId, cursor)
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: "{}"
                    val parsed = json.decodeFromString<PaginatedTasksResponse>(body)
                    _uiState.value = _uiState.value.copy(
                        tasks = _uiState.value.tasks + (parsed.tasks ?: emptyList()),
                        hasMore = parsed.pagination?.hasMore ?: false,
                        nextCursor = parsed.pagination?.nextCursor,
                        isLoading = false,
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading more tasks", e)
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun selectTask(taskId: String) {
        _uiState.value = _uiState.value.copy(selectedTaskId = taskId)
    }

    fun approveTask(taskId: String, request: ApproveTaskRequest) {
        viewModelScope.launch {
            try {
                val requestBody = json.encodeToString(request)
                    .toRequestBody("application/json".toMediaType())
                val response = apiClient.approveTask(orgId, taskId, requestBody)
                if (response.isSuccessful) {
                    Log.i(TAG, "Task $taskId approved")
                } else {
                    Log.w(TAG, "Approve task failed: ${response.code}")
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to approve task (${response.code})",
                    )
                }
                loadTasks()
            } catch (e: Exception) {
                Log.e(TAG, "Error approving task $taskId", e)
                _uiState.value = _uiState.value.copy(
                    error = "Error approving task: ${e.message}",
                )
            }
        }
    }

    fun cancelTask(taskId: String) {
        viewModelScope.launch {
            try {
                val response = apiClient.cancelTask(orgId, taskId)
                if (response.isSuccessful) {
                    Log.i(TAG, "Task $taskId cancelled")
                } else {
                    Log.w(TAG, "Cancel task failed: ${response.code}")
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to cancel task (${response.code})",
                    )
                }
                loadTasks()
            } catch (e: Exception) {
                Log.e(TAG, "Error cancelling task $taskId", e)
                _uiState.value = _uiState.value.copy(
                    error = "Error cancelling task: ${e.message}",
                )
            }
        }
    }

    /**
     * Returns the currently selected task, or null.
     */
    fun getSelectedTask(): TaskResponse? {
        val selectedId = _uiState.value.selectedTaskId ?: return null
        return _uiState.value.tasks.find { it.uuid == selectedId }
    }

    /**
     * Returns only tasks that require user attention.
     */
    fun getActionableTasks(): List<TaskResponse> {
        return _uiState.value.tasks.filter {
            it.status == TaskStatus.NEEDS_ATTENTION || it.status == TaskStatus.PROPOSED
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

