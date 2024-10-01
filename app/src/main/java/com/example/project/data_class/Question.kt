package com.example.project.data_class

data class Question(
    val questionText: String,
    val choices: List<String>,
    val correctAnswerIndex: Int
)
