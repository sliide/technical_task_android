package com.sliide.demoapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserCacheEntity): Long

    @Query("DELETE FROM users WHERE id NOT IN(:userList)")
    suspend fun deleteIfNotInList(userList: List<Int>)

    @Query("SELECT * FROM users ORDER BY timeStamp DESC")
    suspend fun getUsers(): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE id=:userId")
    suspend fun getUser(userId: Int): UserCacheEntity
}