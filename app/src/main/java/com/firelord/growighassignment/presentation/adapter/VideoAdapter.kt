package com.firelord.growighassignment.presentation.adapter

import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.data.model.VideoItem
import com.firelord.growighassignment.databinding.VideoListBinding
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest


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
            Thread {
                Looper.prepare()
                try {
                    val request = YoutubeDLRequest(videoItem.url)
                    request.addOption("-f", "best")
                    val streamInfo = YoutubeDL.getInstance().getInfo(request)
                    setupVideoView(streamInfo.url)
                } catch (e: Exception) {
                    Log.d("DownloadException", e.message.toString())
                }
            }.start()
        }
        fun setupVideoView(videoUrl: String){
            binding.videoView.setOnPreparedListener {
                binding.videoView.start()
            }
            binding.videoView.setVideoURI(Uri.parse(videoUrl))
        }
    }
}