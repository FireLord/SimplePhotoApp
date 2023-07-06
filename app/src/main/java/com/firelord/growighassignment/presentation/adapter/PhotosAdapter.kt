package com.firelord.growighassignment.presentation.adapter

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.firelord.growighassignment.data.model.RemoteFetchItem
import com.firelord.growighassignment.data.model.Urls
import com.firelord.growighassignment.databinding.FeedListBinding

class PhotosAdapter:RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {
    private val photoList = ArrayList<RemoteFetchItem>()

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

    inner class PhotoViewHolder(
        val binding:FeedListBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun bind(remoteFetchItem: RemoteFetchItem) {
            Glide.with(binding.ivImageFetch.context)
                .load(remoteFetchItem.urls.regular)
                .override(2000)
                .transition(withCrossFade())
                .into(binding.ivImageFetch)
        }
    }
}