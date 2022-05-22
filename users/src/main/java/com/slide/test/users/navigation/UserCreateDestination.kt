package com.slide.test.users.navigation

import com.slide.test.core_ui.navigation.destination.NavDestination

/**
 * Created by Stefan Halus on 22 May 2022
 */
object UserCreateDestination : NavDestination {

    private const val routeName: String = "user_create_route"

    override val route = routeName

    override val destination: String = "user_create_destination"

    object Result {
        const val resultArg: String = "user_created"
    }

}