package com.example.my_test_app.data.dto

import com.google.gson.annotations.SerializedName

data class CatalogDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("iconUrl") val iconUrl: String,
    @SerializedName("category") val category: String
)

data class AppDetailsDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("iconUrl") val iconUrl: String,
    @SerializedName("category") val category: String,
    @SerializedName("developer") val developer: String,
    @SerializedName("size") val size: Double,
    @SerializedName("ageRating") val ageRating: Int
)