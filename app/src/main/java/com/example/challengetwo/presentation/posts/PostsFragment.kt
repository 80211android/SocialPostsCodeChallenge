package com.example.challengetwo.presentation.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengetwo.R
import com.example.challengetwo.databinding.FragmentPostsBinding
import com.example.challengetwo.presentation.posts.PostsViewModel.PostsViewEvents
import com.example.challengetwo.presentation.posts.PostsViewModel.PostsViewStates
import com.example.challengetwo.presentation.posts.models.Post
import com.example.challengetwo.presentation.utils.makeGone
import com.example.challengetwo.presentation.utils.makeVisible


class PostsFragment : Fragment() {

    private val viewModel: PostsViewModel by viewModels()
    private lateinit var binding: FragmentPostsBinding
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecyclerView()
        viewModel.getPosts()
    }

    private fun initRecyclerView() {
        postsAdapter = PostsAdapter({})
        with(binding) {
            postsRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = postsAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                viewModel.getPosts()
            }
        }
    }

    private fun initObservers() {
        viewModel.getEventsLiveData().observe(viewLifecycleOwner) { event ->
            handleEventChanges(event)
        }
        viewModel.getStatesLiveData().observe(viewLifecycleOwner) { event ->
            handleStateChanges(event)
        }
    }

    private fun updatePosts(posts: List<Post>) {
        postsAdapter.updatePosts(posts)
        with(binding) {
            postsRecyclerView.makeVisible()
            progressLoader.makeGone()
            errorMessage.makeGone()
            emptyListMessage.makeGone()
        }
    }

    private fun handleEventChanges(event: PostsViewEvents) {
        when (event) {
            is PostsViewEvents.UpdatePostsEvent -> {
                updatePosts(event.posts)
            }
        }
    }

    private fun handleStateChanges(event: PostsViewStates) {
        when (event) {
            is PostsViewStates.LoadingState -> {
                binding.progressLoader.makeVisible()
            }
            PostsViewStates.ErrorState -> {
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        with(binding) {
            errorMessage.makeVisible()
            postsRecyclerView.makeGone()
            progressLoader.makeGone()
            emptyListMessage.makeGone()
        }
    }

    private fun showEmptyPostsState() {
        with(binding) {
            emptyListMessage.makeVisible()
            postsRecyclerView.makeGone()
            progressLoader.makeGone()
            errorMessage.makeGone()
        }
    }

    companion object {
        private const val TAG = "PostsFragment"

        fun newInstance() = PostsFragment()
    }
}
