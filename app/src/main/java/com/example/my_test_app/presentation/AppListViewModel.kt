package com.example.my_test_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppListUiState(
    val apps: List<AppInfo> = emptyList(),
    val showcaseApps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val apps = repository.getApps()
                val showcase = repository.getShowcaseApps()
                _uiState.update {
                    it.copy(
                        apps = apps,
                        showcaseApps = showcase,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
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