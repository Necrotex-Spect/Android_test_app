package com.example.my_test_app

import com.example.my_test_app.data.dto.CatalogDto
import com.example.my_test_app.data.mapper.AppMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class AppMapperTest {

    private val mapper = AppMapper()

    @Test
    fun `toDomain maps CatalogDto to AppInfo correctly`() {
        // Arrange (Подготовка)
        val dto = CatalogDto(
            id = "1",
            name = "Test App",
            description = "Test Description",
            iconUrl = "http://test.com/icon.png",
            category = "Test Category"
        )

        // Act (Действие)
        val domain = mapper.toDomain(dto)

        // Assert (Проверка)
        assertEquals(dto.id, domain.id)
        assertEquals(dto.name, domain.name)
        assertEquals(dto.description, domain.description)
        assertEquals(dto.iconUrl, domain.iconUrl)
        assertEquals(dto.category, domain.category)
    }
}