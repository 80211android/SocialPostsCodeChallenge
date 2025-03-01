package com.example.challengetwo.data

import com.example.challengetwo.presentation.posts.models.Post


interface Repository {

    fun getPosts(): List<Post>
}

class RepositoryImpl(
//    private val client: PostsApi = Service.getPosts,
//    private val map: PostMap = PostMapImpl()
): Repository {
    override fun getPosts(): List<Post> {
        TODO("Not yet implemented")
    }
}