package com.example.mockapiapplication.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class AppEntity(
    @PrimaryKey val id: Int = 1,
    val username: String
)