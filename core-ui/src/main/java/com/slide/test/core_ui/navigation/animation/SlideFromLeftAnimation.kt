package com.slide.test.core_ui.navigation.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
@OptIn(ExperimentalAnimationApi::class)
fun SlideFromLeftAnimation(content: @Composable () -> Unit) {
    val transitionState = remember {
        MutableTransitionState(
            initialState = false
        )
    }.apply { targetState = true }

    AnimatedVisibility(
        visibleState = transitionState,
        modifier = Modifier,
        enter = slideInHorizontally(
            initialOffsetX = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutHorizontally() + fadeOut()) {
        content()
    }
}