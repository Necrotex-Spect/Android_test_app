package com.example.my_test_app

import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import com.example.my_test_app.domain.usecase.GetAppsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAppsUseCaseTest {

    private val repository: AppRepository = mockk()
    private val useCase = GetAppsUseCase(repository)

    @Test
    fun `invoke returns apps from repository`() = runTest {
        // Arrange
        val expectedApps = listOf(
            AppInfo("1", "App1", "Desc1", "cat1", "url1")
        )
        coEvery { repository.getApps() } returns expectedApps

        // Act
        val result = useCase()

        // Assert
        assertEquals(expectedApps, result)
    }
}