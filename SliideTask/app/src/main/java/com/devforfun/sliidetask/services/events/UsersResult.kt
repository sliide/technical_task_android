package com.devforfun.sliidetask.services.events;

import com.devforfun.sliidetask.services.model.User

data class UsersResult(val success: List<User>? = null, val errorStringId: Int? = null)