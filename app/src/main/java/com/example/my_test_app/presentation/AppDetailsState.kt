package com.example.my_test_app.presentation
import com.example.my_test_app.domain.InstallState
import androidx.compose.runtime.Immutable
import com.example.my_test_app.domain.AppInfo

@Immutable
sealed interface AppDetailsState {
    data object Error : AppDetailsState
    data object Loading : AppDetailsState
    data class Content(
        val appDetails: AppInfo,
        val installState: InstallState = InstallState.NotInstalled
    ) : AppDetailsState
}