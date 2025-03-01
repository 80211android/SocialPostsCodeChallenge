package com.example.challengetwo.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengetwo.data.Repository
import com.example.challengetwo.data.RepositoryImpl
import com.example.challengetwo.presentation.posts.models.Post
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {


    private val _postsScreenEventsMutableLiveData: MutableLiveData<PostsViewEvents> = MutableLiveData()

    fun getEventsLiveData(): LiveData<PostsViewEvents> = _postsScreenEventsMutableLiveData

    private val _postsScreenStatesMutableLiveData: MutableLiveData<PostsViewStates> = MutableLiveData()

    fun getStatesLiveData(): LiveData<PostsViewStates> = _postsScreenStatesMutableLiveData

    fun getPosts() = viewModelScope.launch {

        val posts = repository.getPosts()

        _postsScreenEventsMutableLiveData.value = PostsViewEvents.UpdatePostsEvent(posts)

    }


    sealed class PostsViewEvents {
        class UpdatePostsEvent(val posts: List<Post>): PostsViewEvents()
    }

    sealed class PostsViewStates {
        object LoadingState: PostsViewStates()

        object ErrorState: PostsViewStates()
    }
}
