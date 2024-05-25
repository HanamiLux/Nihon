package com.example.nihonhistory.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Celebrates")
data class Celebrate(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String,
    var startDate: Date,
    var endDate: Date,
    var description: String
)