package com.android_test_maverick.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android_test_maverick.UserModel
import retrofit2.http.DELETE

@Dao
interface AccessDao {

    @Query("SELECT * FROM itemsTable")
    fun getAllItems(): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserModel)

    @DELETE
    fun deleteUser(user: UserModel)
}