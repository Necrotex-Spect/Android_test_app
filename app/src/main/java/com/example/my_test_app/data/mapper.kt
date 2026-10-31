package com.example.my_test_app.data.mapper

import com.example.my_test_app.data.AppDto
import com.example.my_test_app.domain.AppInfo

fun AppDto.toDomain(): AppInfo {
    return AppInfo(
        name = this.name,
        description = this.description,
        category = this.category,
        iconResId = this.iconResId
    )
}