package com.sliide.presentation.components

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun SnackMessage(
    message: String,
    actionLabel: String,
    duration: SnackbarDuration,
    action: () -> Unit = {},
    dismissed: () -> Unit = {}
) {
    if (message.isNotEmpty()) {
        val snackBarHostState = remember { SnackbarHostState() }
        LaunchedEffect(key1 = message) {
            val result = snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )

            when (result) {
                SnackbarResult.Dismissed -> dismissed()
                SnackbarResult.ActionPerformed -> action()
            }
        }
        SnackbarHost(modifier = Modifier.navigationBarsPadding(), hostState = snackBarHostState)
    }
}