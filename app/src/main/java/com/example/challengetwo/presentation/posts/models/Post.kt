package com.example.challengetwo.presentation.posts.models

data class Post(
    val userId: String,
    val userName: String,
    val comment: String,
    val like: Boolean,
    val photoUrl: String
)
