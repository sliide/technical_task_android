package com.slide.test.users.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.slide.test.core_ui.navigation.destination.NavDestination

/**
 * Created by Stefan Halus on 22 May 2022
 */
object UserDeleteDestination : NavDestination {

    private const val routeName: String = "user_delete_route"

    override val route = "${routeName}/{${Input.userIdArg}}?${Input.userNameArg}={${Input.userNameArg}}"

    override val destination: String = "user_delete_destination"

    val arguments = listOf(
        navArgument(Input.userIdArg) {
            type = NavType.LongType
        },
        navArgument(Input.userNameArg) {
            type = NavType.StringType
        }
    )

    fun createRoute(userId: Long, userName: String) : String {
        return "${routeName}/$userId?${Input.userNameArg}=${userName}"
    }

    object  Input {
        const val userIdArg: String = "userIdArg"
        const val userNameArg: String = "userNameArg"
    }

    object Result {
        const val resultArg: String = "user_deleted"
    }

}