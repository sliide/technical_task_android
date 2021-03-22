package com.devforfun.sliidetask.services.events;

import com.devforfun.sliidetask.services.model.User

data class CreateUserResult(val success: User? = null, val error: Int? = null)