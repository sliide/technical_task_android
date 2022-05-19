package com.slide.test.users.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.slide.test.core_ui.navigation.destination.NavDestination
import com.slide.test.users.listing.UsersRoute

/**
 * Created by Stefan Halus on 18 May 2022
 */
object UsersDestination : NavDestination {
    override val route: String = "users_route"
    override val destination: String = "users_destination"
}

fun NavGraphBuilder.usersGraph(
    //windowSizeClass: WindowSizeClass
) {
    composable(route = UsersDestination.route) {
        EnterAnimation() {
            UsersRoute(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun EnterAnimation(content: @Composable () -> Unit) {
    val transitionState = remember {
        MutableTransitionState(
            initialState = false
        )
    }.apply { targetState = true }

    AnimatedVisibility(
        visibleState = transitionState,
        modifier = Modifier,
        enter = slideInHorizontally (
            initialOffsetX = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutHorizontally() + fadeOut()) {
        content()
    }
}