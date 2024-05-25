package com.example.nihonhistory.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Selected_celebrates",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Celebrate::class,
            parentColumns = ["id"],
            childColumns = ["celebrate_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class SelectedCelebrate(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var user_id: Int,
    var celebrate_id: Int,
)