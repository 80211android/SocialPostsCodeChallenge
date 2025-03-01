package com.example.challengetwo.data

import com.example.challengetwo.presentation.posts.models.Post

interface Repository {

    fun getPosts(): List<Post>
}

class RepositoryImpl(
    private val servie: MockPosts = MockPosts
): Repository {
    override fun getPosts(): List<Post> {
        return servie.getPosts
    }
}
