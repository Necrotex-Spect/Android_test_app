package com.example.my_test_app.presentation

import androidx.lifecycle.ViewModel
import com.example.my_test_app.data.AppRepositoryImpl
import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppListUiState(
    val apps: List<AppInfo> = emptyList(),
    val showcaseApps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

class AppListViewModel(
    private val repository: AppRepository = AppRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true) }

        val apps = repository.getApps()
        val showcase = repository.getShowcaseApps()

        _uiState.update {
            it.copy(
                apps = apps,
                showcaseApps = showcase,
                isLoading = false
            )
        }
    }

    fun onToolbarClick() {
        _uiState.update {
            it.copy(isSnackbarVisible = true, snackbarMessage = "Вы кликнули на тулбар!")
        }
    }

    fun onSnackbarShown() {
        _uiState.update { it.copy(isSnackbarVisible = false, snackbarMessage = "") }
    }
}