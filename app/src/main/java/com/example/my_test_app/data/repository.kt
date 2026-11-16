package com.example.my_test_app.data

import com.example.my_test_app.data.api.AppApi
import com.example.my_test_app.data.mapper.AppMapper
import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    // --- Новые методы для загрузки рейтингов ---

    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            ratings[id] = api.getRating(id)
        }
        return ratings
    }

    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async {
                id to api.getRating(id)
            }
        }.awaitAll().toMap()
    }
}