package com.example.my_test_app

import com.example.my_test_app.data.AppRepositoryImpl // <-- ИСПРАВЛЕННЫЙ ИМПОРТ
import com.example.my_test_app.data.api.AppApi
import com.example.my_test_app.data.dto.CatalogDto
import com.example.my_test_app.data.mapper.AppMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppRepositoryTest {

    private lateinit var repository: AppRepositoryImpl
    private val api: AppApi = mockk()
    private val mapper = AppMapper()

    @Before
    fun setUp() {
        repository = AppRepositoryImpl(api, mapper)
    }

    @Test
    fun `getApps returns mapped list from api`() = runTest {
        // Arrange
        val dtoList = listOf(
            CatalogDto("1", "App1", "Desc1", "url1", "cat1"),
            CatalogDto("2", "App2", "Desc2", "url2", "cat2")
        )
        coEvery { api.getCatalog() } returns dtoList

        // Act
        val result = repository.getApps()

        // Assert
        assertEquals(2, result.size)
        assertEquals("App1", result[0].name)
        assertEquals("App2", result[1].name)
    }
}