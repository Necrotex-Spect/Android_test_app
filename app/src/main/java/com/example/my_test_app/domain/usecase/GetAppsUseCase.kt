package com.example.my_test_app.domain.usecase

import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): List<AppInfo> {
        return repository.getApps()
    }
}