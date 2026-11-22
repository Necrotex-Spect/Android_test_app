package com.example.my_test_app.presentation
import com.example.my_test_app.domain.InstallState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_test_app.domain.AppInfo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppDetailsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private var installJob: Job? = null

    // Инициализация состояния переданным объектом AppInfo
    fun setAppDetails(app: AppInfo?) {
        if (app == null) {
            _state.value = AppDetailsState.Error
            return
        }
        _state.value = AppDetailsState.Content(appDetails = app)
    }

    fun onInstallClick() {
        val currentState = _state.value
        if (currentState !is AppDetailsState.Content) return

        if (currentState.installState is InstallState.Installing) {
            // Отмена установки
            installJob?.cancel()
            installJob = null
            _state.update {
                (it as AppDetailsState.Content).copy(installState = InstallState.NotInstalled)
            }
        } else {
            // Запуск установки
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        try {
            for (progress in 0..100 step 5) {
                delay(150)
                _state.update { current ->
                    if (current is AppDetailsState.Content) {
                        current.copy(installState = InstallState.Installing(progress))
                    } else current
                }
            }
            // По окончании
            _state.update { current ->
                if (current is AppDetailsState.Content) {
                    current.copy(installState = InstallState.Installed)
                } else current
            }
        } catch (e: Exception) {
            // Корутина отменена — состояние уже сброшено в onInstallClick
        }
    }
}