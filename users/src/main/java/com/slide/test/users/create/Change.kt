package com.slide.test.users.create


/**
 * Created by Stefan Halus on 19 May 2022
 */

sealed class Change {

    object Loading : Change()

    data class UserCreateSuccess(val createdUserName: String) : Change()

    data class UserCreateError(val userName: String, val email: String, val genderIndex:Int, val error: Throwable) : Change()

    data class UserFormUpdate(val userName: String?, val email: String?, val genderIndex:Int?): Change()

    object UserCreationAcknowledged : Change()
}
