package com.firelord.growighassignment.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.firelord.growighassignment.R
import com.firelord.growighassignment.data.model.RemoteFetchItem
import com.firelord.growighassignment.databinding.FeedListBinding
import kotlin.random.Random

class PhotosAdapter:RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {
    private val photoList = ArrayList<RemoteFetchItem>()
    private var onItemClickListener: ((position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setList(photos: List<RemoteFetchItem>){
        photoList.clear()
        photoList.addAll(photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = FeedListBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun getItem(position: Int): RemoteFetchItem {
        return photoList[position]
    }

    inner class PhotoViewHolder(
        val binding:FeedListBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun bind(remoteFetchItem: RemoteFetchItem) {
            Glide.with(binding.ivImageFetch.context)
                .load(remoteFetchItem.urls.regular)
                .override(2000)
                .transition(withCrossFade())
                .into(binding.ivImageFetch)
            binding.textView14.setOnClickListener {
                sharePhotoUrl(remoteFetchItem.urls.regular,it.context)
            }

            val random = Random(System.currentTimeMillis())
            var number = 0
            var numberComment = 0
            var isLiked = false
            number = random.nextInt(1000)
            binding.textView12.text = "${number} Likes"

            numberComment = random.nextInt(1000)
            binding.textView13.text = "${numberComment} Comments"

            binding.textView13.setOnClickListener {
                numberComment+=1
                binding.textView13.text = "${numberComment} Comments"
                onItemClickListener?.invoke(position)
            }

            binding.textView12.setOnClickListener {
                if (isLiked) {
                    // If already liked, remove the like
                    isLiked = false
                    number -= 1
                    binding.textView12.text = "${number} Likes"
                    val updatedHeartIcon =
                        ContextCompat.getDrawable(it.context, R.drawable.ic_heart)
                    binding.textView12.setCompoundDrawablesWithIntrinsicBounds(
                        updatedHeartIcon,
                        null,
                        null,
                        null
                    )
                } else {
                    // If not liked, add the like and remove the dislike
                    isLiked = true
                    number += 1
                    binding.textView12.text = "${number} Likes"
                    val updatedHeartIcon =
                        ContextCompat.getDrawable(it.context, R.drawable.ic_heart_filled_feed)
                    binding.textView12.setCompoundDrawablesWithIntrinsicBounds(
                        updatedHeartIcon,
                        null,
                        null,
                        null
                    )
                }
            }
        }

        fun sharePhotoUrl(videoUrl: String, context: Context) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrl)
            context.startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}