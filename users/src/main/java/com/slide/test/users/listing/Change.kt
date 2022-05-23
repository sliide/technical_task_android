package com.slide.test.users.listing

import com.slide.test.users.model.UserUI


/**
 * Created by Stefan Halus on 19 May 2022
 */

sealed class Change {

    object Loading : Change()

    data class UserList(val userList: List<UserUI>) : Change()

    data class Error(val throwable: Throwable) : Change()

    object EmptyUserList : Change()
}
