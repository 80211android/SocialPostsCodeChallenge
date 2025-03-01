package com.example.challengetwo.data.model

data class Post(
    val userId: String,
    val userName: String,
    val comment: String,
    val like: Boolean,
    val photoUrl: String
)
