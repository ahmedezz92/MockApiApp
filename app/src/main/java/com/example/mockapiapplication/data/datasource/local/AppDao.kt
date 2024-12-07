package com.example.mockapiapplication.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsername(user: AppEntity)

    @Query("SELECT username FROM user_table WHERE id = 1")
    suspend fun getUsername(): String?
}