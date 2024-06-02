package com.example.nihonhistory.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var password: String,
    var email: String
)