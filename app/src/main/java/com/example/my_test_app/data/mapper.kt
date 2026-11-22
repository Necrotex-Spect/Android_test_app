package com.example.my_test_app.data.mapper

import com.example.my_test_app.data.dto.AppDetailsDto
import com.example.my_test_app.data.dto.CatalogDto
import com.example.my_test_app.domain.AppInfo
import javax.inject.Inject

class AppMapper @Inject constructor() {
    fun toDomain(dto: CatalogDto): AppInfo {
        return AppInfo(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            category = dto.category,
            iconUrl = dto.iconUrl
        )
    }

    fun toDomain(dto: AppDetailsDto): AppInfo {
        return AppInfo(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            category = dto.category,
            iconUrl = dto.iconUrl
        )
    }
}