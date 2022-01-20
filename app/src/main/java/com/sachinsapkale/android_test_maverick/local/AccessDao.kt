package com.android_test_maverick.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android_test_maverick.SingleItemModel

@Dao
interface AccessDao {

    @Query("SELECT * FROM itemsTable")
    fun getAllItems(): List<SingleItemModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<SingleItemModel>)
}