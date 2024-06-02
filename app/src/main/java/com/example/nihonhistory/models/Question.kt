package com.example.nihonhistory.models

data class Question(
    val period: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String
)
