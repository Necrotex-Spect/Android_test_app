package com.example.my_test_app.domain

interface AppRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getShowcaseApps(): List<AppInfo>
    suspend fun getAppDetails(id: String): AppInfo
}