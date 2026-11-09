package com.example.my_test_app.data.mapper

import com.example.my_test_app.data.AppDto
import com.example.my_test_app.domain.AppInfo
import javax.inject.Inject

class AppMapper @Inject constructor() {
    fun toDomain(dto: AppDto): AppInfo {
        return AppInfo(
            name = dto.name,
            description = dto.description,
            category = dto.category,
            iconResId = dto.iconResId
        )
    }
}