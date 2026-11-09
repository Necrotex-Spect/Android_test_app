package com.example.my_test_app.data

import com.example.my_test_app.data.api.AppApi
import com.example.my_test_app.data.mapper.AppMapper
import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val mapper: AppMapper
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> {
        val catalog = api.getCatalog()
        return catalog.map { mapper.toDomain(it) }
    }

    override suspend fun getShowcaseApps(): List<AppInfo> {
        val catalog = api.getCatalog()
        return catalog.take(9).map { mapper.toDomain(it) }
    }

    override suspend fun getAppDetails(id: String): AppInfo {
        val details = api.getAppDetails(id)
        return mapper.toDomain(details)
    }
}