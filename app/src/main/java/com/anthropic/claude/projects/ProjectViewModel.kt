package com.anthropic.claude.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.repository.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Projects screen and project detail view.
 *
 * Manages project listing, creation, and project document (knowledge) management.
 */
class ProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val orgId: String,
) : ViewModel() {

    private val _state = MutableStateFlow<ProjectListState>(ProjectListState.Loading)
    val state: StateFlow<ProjectListState> = _state.asStateFlow()

    private val _detail = MutableStateFlow<ProjectDetailState?>(null)
    val detail: StateFlow<ProjectDetailState?> = _detail.asStateFlow()

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _state.value = ProjectListState.Loading
            try {
                val projects = projectRepository.listProjects(orgId)
                _state.value = if (projects.isEmpty()) {
                    ProjectListState.Empty
                } else {
                    ProjectListState.Loaded(projects.map { it.toUiModel() })
                }
            } catch (e: Exception) {
                _state.value = ProjectListState.Error(
                    e.message ?: "Failed to load projects"
                )
            }
        }
    }

    fun createProject(name: String, description: String = "") {
        viewModelScope.launch {
            try {
                projectRepository.createProject(orgId, name, description)
                loadProjects()
            } catch (e: Exception) {
                _state.value = ProjectListState.Error(
                    "Failed to create project: ${e.message}"
                )
            }
        }
    }

    fun loadProjectDetail(projectId: String) {
        viewModelScope.launch {
            _detail.value = ProjectDetailState(isLoading = true)
            try {
                val project = projectRepository.getProject(orgId, projectId)
                val docs = projectRepository.listProjectDocs(orgId, projectId)
                _detail.value = ProjectDetailState(
                    project = project?.toUiModel(),
                    documents = docs.map { doc ->
                        ProjectDocument(
                            id = doc.uuid,
                            fileName = doc.fileName,
                            contentType = doc.contentType,
                            sizeBytes = doc.sizeBytes,
                            createdAt = doc.createdAt,
                        )
                    },
                    isLoading = false,
                )
            } catch (e: Exception) {
                _detail.value = ProjectDetailState(
                    error = e.message ?: "Failed to load project",
                    isLoading = false,
                )
            }
        }
    }

    fun deleteProject(projectId: String) {
        viewModelScope.launch {
            try {
                projectRepository.deleteProject(orgId, projectId)
                loadProjects()
            } catch (e: Exception) {
                _state.value = ProjectListState.Error(
                    "Failed to delete project: ${e.message}"
                )
            }
        }
    }

    private fun com.anthropic.claude.db.entity.ProjectEntity.toUiModel(): ProjectUiModel {
        return ProjectUiModel(
            id = this.uuid,
            name = this.name,
            description = this.description,
            isStarred = this.isStarred,
            createdAt = this.createdAt,
            conversationCount = 0,
        )
    }
}

// ── Models ───────────────────────────────────────────────────────────────

sealed interface ProjectListState {
    data object Loading : ProjectListState
    data class Loaded(val projects: List<ProjectUiModel>) : ProjectListState
    data object Empty : ProjectListState
    data class Error(val message: String) : ProjectListState
}

data class ProjectUiModel(
    val id: String,
    val name: String,
    val description: String? = null,
    val isStarred: Boolean = false,
    val createdAt: Long = 0L,
    val conversationCount: Int = 0,
)

data class ProjectDetailState(
    val project: ProjectUiModel? = null,
    val documents: List<ProjectDocument> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class ProjectDocument(
    val id: String,
    val fileName: String,
    val contentType: String? = null,
    val sizeBytes: Long = 0L,
    val createdAt: Long = 0L,
)
