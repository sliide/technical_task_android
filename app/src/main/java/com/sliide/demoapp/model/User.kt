package com.sliide.demoapp.model

/**
 *  Model class that represents User
 *  @param id - user unique identifier
 *  @param name - user name
 *  @param email - user email
 *  @param gender - user gender
 *  @param status - user status
 *  @param creationRelativeTime - relative time from when the user was first fetch from the api
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val gender: Gender,
    val status: Status,
    val creationRelativeTime: String = "just now"
)
