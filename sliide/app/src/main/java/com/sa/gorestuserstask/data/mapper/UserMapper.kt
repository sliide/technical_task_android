package com.sa.gorestuserstask.data.mapper

import com.sa.gorestuserstask.data.remote.UserApiModel
import com.sa.gorestuserstask.data.remote.UserApiRequest
import com.sa.gorestuserstask.domain.entity.Gender
import com.sa.gorestuserstask.domain.entity.Status
import com.sa.gorestuserstask.domain.entity.User

class UserMapper {

    fun mapFromDtoToDomain(userApiModel: UserApiModel) = User(
        id = userApiModel.id,
        name = userApiModel.name,
        email = userApiModel.email,
        gender = when (userApiModel.gender) {
            FEMALE -> Gender.Female
            else -> Gender.Male
        },
        status = when (userApiModel.status) {
            ACTIVE -> Status.Active
            else -> Status.Inactive
        }
    )

    fun mapFromDomainToDto(user: User) = UserApiRequest(
        name = user.name,
        email = user.email,
        gender = when (user.gender) {
            Gender.Female -> FEMALE
            Gender.Male -> MALE
        },
        status = when (user.status) {
            Status.Active -> ACTIVE
            Status.Inactive -> INACTIVE
        }
    )

    companion object {
        private const val FEMALE = "female"
        private const val MALE = "male"
        private const val ACTIVE = "active"
        private const val INACTIVE = "inactive"
    }
}



