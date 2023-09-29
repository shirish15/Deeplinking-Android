package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.composables.MainScreenComposable
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.Constants.OPEN_URL
import com.example.myapplication.viewmodels.MainEvents
import com.example.myapplication.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().build()
            MyApplicationTheme {
                val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
                MainScreenComposable(
                    loading = uiState.loading,
                    apiError = uiState.apiError,
                    urlParameters = uiState.urlParameters,
                    apiResponse = uiState.apiResponse,
                    showBrowserDialog = uiState.showBrowserDialog,
                    showDialog = uiState.showDialog,
                    setEvents = mainViewModel::setEvents
                ) { directions ->
                    when (directions) {
                        NavigationDirections.OpenBrowser -> {
                            customTabsIntent.launchUrl(this, Uri.parse(OPEN_URL))
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val data: Uri? = intent?.data
        if (data != null && data.toString().isNotEmpty()) {
            mainViewModel.setEvents(MainEvents.SetDeepLink(url = data.toString()))
        }
    }
}

sealed interface NavigationDirections {
    object OpenBrowser : NavigationDirections
}