package com.slide.test.users.delete


/**
 * Created by Stefan Halus on 19 May 2022
 */

sealed class Change {

    object Loading : Change()

    object DeleteUserSuccess : Change()

    data class DeleteUserError(val error: Throwable) : Change()

    object UserDeleteAcknowledged : Change()
}
