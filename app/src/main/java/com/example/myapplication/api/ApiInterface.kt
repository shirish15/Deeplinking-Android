package com.example.myapplication.api

import com.example.myapplication.models.ApiData
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.GET

interface ApiInterface {
    @GET("/api/v0/product/20106836.json")
    suspend fun callApi(): ApiData
}
