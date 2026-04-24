package com.anthropic.claude.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.project.Project
import com.anthropic.claude.api.project.ProjectDoc
import com.anthropic.claude.api.project.ProjectCreateParams
import com.anthropic.claude.api.project.ProjectUpdateParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the project details screen. */
data class ProjectUiState(
    val project: Project? = null,
    val docs: List<ProjectDoc> = emptyList(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for project creation, editing, and details.
 */
class ProjectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectUiState())
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    fun loadProject(projectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getProject(projectId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun createProject(params: ProjectCreateParams) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            // TODO: apiClient.createProject(params)
            _uiState.value = _uiState.value.copy(isSaving = false)
        }
    }

    fun updateProject(projectId: String, params: ProjectUpdateParams) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            // TODO: apiClient.updateProject(projectId, params)
            _uiState.value = _uiState.value.copy(isSaving = false)
        }
    }

    fun archiveProject(projectId: String) {
        viewModelScope.launch {
            updateProject(projectId, ProjectUpdateParams(isArchived = true))
        }
    }

    fun starProject(projectId: String, starred: Boolean) {
        viewModelScope.launch {
            updateProject(projectId, ProjectUpdateParams(isStarred = starred))
        }
    }

    fun loadDocs(projectId: String) {
        viewModelScope.launch {
            // TODO: apiClient.getProjectDocs(projectId)
        }
    }

    fun deleteDoc(projectId: String, docId: String) {
        viewModelScope.launch {
            // TODO: apiClient.deleteProjectDoc(projectId, docId)
            loadDocs(projectId)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
