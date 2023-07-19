package com.firelord.growighassignment.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.data.model.VideoItem
import com.firelord.growighassignment.databinding.VideoListBinding

class VideoAdapter :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val videoList = ArrayList<VideoItem>()

    fun setList(videoItem: List<VideoItem>){
        videoList.clear()
        videoList.addAll(videoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoListBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    class VideoViewHolder(val binding:VideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(videoItem: VideoItem){
            binding.videoView.setVideoURI(Uri.parse(videoItem.url))
        }
    }
}