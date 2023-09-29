package com.example.myapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    val status: Long?,
    val product: ProductData?,
    val code: String
)

@JsonClass(generateAdapter = true)
data class ProductData(
    val complete: Int?,
    val selected_images: SelectedImagesData?,
    val lang: String?,
    val quantity: String?,
    val ingredients_n: String?,
    val rev: String?
)

@JsonClass(generateAdapter = true)
data class SelectedImagesData(
    val nutrition: GenericData?,
    val ingredients: GenericData?
)

@JsonClass(generateAdapter = true)
data class GenericData(
    val display: GenericSubData?,
    val small: GenericSubData?,
    val thumb: GenericSubData?
)

@JsonClass(generateAdapter = true)
data class GenericSubData(
    val fr: String?
)