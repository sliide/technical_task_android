package com.sliide.demoapp.room

import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.mapper.EntityMapper
import javax.inject.Inject

class UserCacheMapper
@Inject
constructor() : EntityMapper<UserCacheEntity, User> {

    override fun mapFromEntity(entity: UserCacheEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            gender = entity.gender,
            status = entity.status,
            creationRelativeTime = getTimeAgo(entity.timeStamp)
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserCacheEntity {
        return UserCacheEntity(
            id = domainModel.id,
            name = domainModel.name,
            email = domainModel.email,
            gender = domainModel.gender,
            status = domainModel.status,
            timeStamp = System.currentTimeMillis()
        )
    }

    fun mapFromEntitiesList(entityList: List<UserCacheEntity>) =
        entityList.map { mapFromEntity(it) }

    private fun getTimeAgo(time: Long): String {
        val diff = System.currentTimeMillis() - time
        when {
            diff < MINUTE_MILLIS -> {
                return "just now";
            }
            diff < 2 * MINUTE_MILLIS -> {
                return "a minute ago"
            }
            diff < 50 * MINUTE_MILLIS -> {
                return "${diff / MINUTE_MILLIS} minutes ago"
            }
            diff < 120 * MINUTE_MILLIS -> {
                return "an hour ago";
            }
            diff < 24 * HOUR_MILLIS -> {
                return "${diff / HOUR_MILLIS}  hours ago"
            }
            diff < 48 * HOUR_MILLIS -> {
                return "yesterday";
            }
            else -> {
                return "${diff / DAY_MILLIS} days ago"
            }
        }
    }

    companion object {
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
    }

}