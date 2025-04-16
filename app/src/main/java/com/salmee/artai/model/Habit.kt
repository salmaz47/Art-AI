package com.salmee.artai.model

data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val imageResId: Int // This will store drawable resource ID
)