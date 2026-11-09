package com.example.my_test_app.data.api

import com.example.my_test_app.data.dto.AppDetailsDto
import com.example.my_test_app.data.dto.CatalogDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogDto>

    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto
}