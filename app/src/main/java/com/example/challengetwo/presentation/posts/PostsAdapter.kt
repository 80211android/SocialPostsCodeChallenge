package com.example.challengetwo.presentation.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.challengetwo.R
import com.example.challengetwo.databinding.PostItemBinding

import com.example.challengetwo.presentation.posts.models.Post

class PostsAdapter(
    val onClickListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val posts = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val postsItemBinding = DataBindingUtil.inflate<PostItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.post_item,
            parent,
            false
        )
        return PostDetailViewHolder(postsItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostDetailViewHolder).bind(posts[position])
    }


    fun updatePosts(post: List<Post>) {
        posts.clear()
        posts.addAll(post)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = posts.size

    class PostDetailViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            with(binding) {
                name.text = post.userName
                comment.text = post.comment

                like.text = post.like.toString()
                Glide
                    .with(itemView)
                    .load(post.photoUrl)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(photo);
            }
        }
    }
}
