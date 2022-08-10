package com.sliide.demoapp.retrofit

import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.mapper.EntityMapper
import javax.inject.Inject

class UserNetworkMapper
@Inject
constructor() : EntityMapper<UserNetworkEntity, User> {
    override fun mapFromEntity(entity: UserNetworkEntity): User {
        return User(
            id = entity.id ?: 0,
            email = entity.email,
            name = entity.name,
            gender = Gender.values().firstOrNull { it.genderValue == entity.gender } ?: Gender.MALE,
            status = Status.values().firstOrNull { it.statusValue == entity.gender } ?: Status.ACTIVE,
            creationRelativeTime = "just now"
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserNetworkEntity {
        return UserNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            email = domainModel.email,
            gender = domainModel.gender.genderValue,
            status = domainModel.status.statusValue
        )
    }

    fun mapFromEntitiesList(entityList: List<UserNetworkEntity>) =
        entityList.map { mapFromEntity(it) }
}