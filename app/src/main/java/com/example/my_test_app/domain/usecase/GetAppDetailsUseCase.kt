package com.example.my_test_app.domain.usecase

import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): AppInfo {
        return repository.getAppDetails(id)
    }
}