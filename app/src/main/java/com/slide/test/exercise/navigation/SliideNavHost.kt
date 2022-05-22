package com.slide.test.exercise.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.slide.test.users.navigation.UsersDestination
import com.slide.test.users.navigation.usersGraph

/**
 * Created by Stefan Halus on 18 May 2022
 */
@Composable
fun SliideNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        usersGraph(navController)
    }

}