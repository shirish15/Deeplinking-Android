package com.example.myapplication.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.myapplication.NavigationDirections
import com.example.myapplication.R
import com.example.myapplication.viewmodels.MainEvents

@Composable
fun MainScreenComposable(
    loading: Boolean,
    urlParameters: Map<String, String>,
    showBrowserDialog: Boolean,
    apiError: Int?,
    showDialog: Boolean,
    apiResponse: String,
    setEvents: (MainEvents) -> Unit,
    navigate: (NavigationDirections) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    if (apiError != null) {
        LaunchedEffect(key1 = Unit, block = {
            Toast.makeText(context, apiError, Toast.LENGTH_LONG).show()
        })
    }
    if (showDialog || showBrowserDialog) {
        AlertDialog(
            modifier = Modifier.height(screenWidth + 60.dp),
            onDismissRequest = {
                setEvents(MainEvents.HideDialog)
            }, dismissButton = {
                ButtonComposable(btnText = stringResource(R.string.dismiss)) {
                    setEvents(MainEvents.HideDialog)
                }
            }, confirmButton = {
                if (!showBrowserDialog) {
                    ButtonComposable(btnText = stringResource(R.string.copy)) {
                        clipboardManager.setText(AnnotatedString((apiResponse)))
                    }
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (showBrowserDialog) {
                        var char = 'x'
                        urlParameters.forEach { (name, value) ->
                            Bullet(text = "$name ($char) : $value")
                            char++
                        }
                    } else {
                        Text(text = apiResponse)
                    }
                }
            })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!loading) {
            ButtonComposable(
                btnText = stringResource(R.string.open_in_app_browser),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                navigate(NavigationDirections.OpenBrowser)
            }
            ButtonComposable(btnText = stringResource(R.string.call_api)) {
                setEvents(MainEvents.CallApi)
            }
        } else {
            CircularProgressIndicator(
                color = Color.LightGray,
                strokeWidth = 8.dp
            )
        }
    }
}

@Composable
private fun ButtonComposable(modifier: Modifier = Modifier, btnText: String, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(
            text = btnText,
            style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
        )
    }
}

//ui for bullet point
@Composable
private fun Bullet(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(5.dp)
                .background(Color.Black, shape = CircleShape)
        )
        Text(text = text)
    }
}
