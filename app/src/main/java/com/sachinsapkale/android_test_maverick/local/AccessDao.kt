package com.sachin_sapkale_android_challenge.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sachin_sapkale_android_challenge.SingleItemModel

@Dao
interface AccessDao {

    @Query("SELECT * FROM itemsTable")
    fun getAllItems(): List<SingleItemModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<SingleItemModel>)
}