package com.example.my_test_app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppListUiState(
    val apps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

class AppListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _uiState.update { it.copy(isLoading = true) }
        // Имитация загрузки
        _uiState.update { it.copy(apps = sampleApps, isLoading = false) }
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