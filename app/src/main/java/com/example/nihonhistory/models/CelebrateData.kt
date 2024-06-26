package com.example.nihonhistory.models

import java.util.Date

data class CelebrateData(
    val date: Date,
    val name: String,
    val name_en: String,
    val name_ru: String,
    val week: String,
    val week_en: String,
    val description: String
)
