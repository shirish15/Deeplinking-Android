package com.example.myapplication.repo

import com.example.myapplication.models.ApiData
import com.example.myapplication.utils.ApiResultWrapper

interface ApiRepo {
    suspend fun callApi(): ApiResultWrapper<String>
}