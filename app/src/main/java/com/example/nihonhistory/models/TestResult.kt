package com.example.nihonhistory.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Test_results",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class TestResult(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var user_id: Int,
    var iwajuku_test: Double,
    var jomon_test: Double,
    var yayoi_test: Double,
    var yamato_test: Double,
    var nara_test: Double,
    var heyan_test: Double,
    var kamakura_test: Double,
    var muromachi_test: Double,
    var azuchi_momoyama_test: Double,
    var general_test: Double
)
