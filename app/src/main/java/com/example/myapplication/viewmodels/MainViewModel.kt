package com.example.myapplication.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.ApiData
import com.example.myapplication.repo.ApiRepo
import com.example.myapplication.repo.impl.ApiRepoImpl
import com.example.myapplication.utils.ApiResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.myapplication.R
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.lang.Exception
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainViewModel constructor(private val repo: ApiRepo = ApiRepoImpl()) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    //function to handle events
    fun setEvents(events: MainEvents) {
        when (events) {
            MainEvents.CallApi -> {
                callApi()
            }

            MainEvents.HideDialog -> {
                _uiState.update {
                    it.copy(showDialog = false, showBrowserDialog = false)
                }
            }

            is MainEvents.SetDeepLink -> {
                val uri = Uri.parse(events.url)
                val paramsNameList = uri.queryParameterNames
                val params: MutableMap<String, String> = mutableMapOf()
                paramsNameList?.forEach {
                    it?.let {
                        if (it.equals("data", ignoreCase = true)) {
                            try {
                                val decodedData = URLDecoder.decode(
                                    uri.getQueryParameter(it).orEmpty(),
                                    StandardCharsets.UTF_8.toString()
                                )
                                params[it] = JSONObject(decodedData).toString()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            params[it] = uri.getQueryParameter(it).orEmpty()
                        }
                    }
                }
                _uiState.update {
                    it.copy(showBrowserDialog = true, urlParameters = params)
                }
            }
        }
    }

    //function which calls the API
    private fun callApi() {
        _uiState.update {
            it.copy(loading = true, apiError = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repo.callApi()) {
                is ApiResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(loading = false, apiError = R.string.something_went_wrong)
                    }
                }

                is ApiResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            apiResponse = result.data,
                            showDialog = true
                        )
                    }
                }
            }
        }
    }

    //data class for viewmodel mutable states
    data class MainUiState(
        val loading: Boolean = false,
        val urlParameters: Map<String, String> = emptyMap(),
        val showDialog: Boolean = false,
        val showBrowserDialog: Boolean = false,
        val apiResponse: String = "",
        val apiError: Int? = null,
    )
}

//events fired from UI
sealed interface MainEvents {
    object CallApi : MainEvents
    object HideDialog : MainEvents
    data class SetDeepLink(val url: String) : MainEvents
}