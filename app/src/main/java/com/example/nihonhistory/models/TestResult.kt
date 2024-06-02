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
    var iwajuku_test: Int,
    var jomon_test: Int,
    var yayoi_test: Int,
    var yamato_test: Int,
    var nara_test: Int,
    var heian_test: Int,
    var kamakura_test: Int,
    var muromachi_test: Int,
    var azuchi_momoyama_test: Int,
    var general_test: Int
)
