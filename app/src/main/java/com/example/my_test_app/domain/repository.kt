package com.example.my_test_app.domain

interface AppRepository {
    fun getApps(): List<AppInfo>
    fun getShowcaseApps(): List<AppInfo>
}