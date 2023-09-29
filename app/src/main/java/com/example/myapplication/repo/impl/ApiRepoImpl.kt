package com.example.myapplication.repo.impl

import android.util.Log
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.repo.ApiRepo
import com.example.myapplication.utils.ApiResultWrapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class ApiRepoImpl() : ApiRepo {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://world.openpetfoodfacts.org")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val apiService = retrofit.create(ApiInterface::class.java)

    override suspend fun callApi(): ApiResultWrapper<String> {
        return try {
            val res = apiService.callApi()
            val gson = GsonBuilder().setPrettyPrinting().create()
            val response = gson.toJson(res)
            Log.e("API RESPONSE", "callApi: $response")
            ApiResultWrapper.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResultWrapper.Error(e.toString())
        }
    }
}