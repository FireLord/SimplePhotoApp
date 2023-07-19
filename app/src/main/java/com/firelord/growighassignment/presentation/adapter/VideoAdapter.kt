package com.firelord.growighassignment.presentation.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.data.model.VideoItem
import com.firelord.growighassignment.databinding.VideoListBinding
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        holder.onRecycled()
    }

    class VideoViewHolder(val binding:VideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        private var videoJob: Job? = null
        fun bind(videoItem: VideoItem){
            videoJob?.cancel()
            binding.progressBarVideo.visibility = View.VISIBLE
            videoJob = CoroutineScope(IO).launch {
                    try {
                        val request = YoutubeDLRequest(videoItem.url)
                        request.addOption("-f", "best")
                        val streamInfo = YoutubeDL.getInstance().getInfo(request)

                        withContext(Dispatchers.Main) {
                            setupVideoView(streamInfo.url)
                            binding.progressBarVideo.visibility = View.GONE
                            Log.d("Download", streamInfo.url)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            binding.progressBarVideo.visibility = View.GONE
                            Log.d("DownloadException", e.message.toString())
                        }
                    }
                }
        }
        fun setupVideoView(videoUrl: String){
            binding.videoView.setOnPreparedListener {
                binding.videoView.start()
            }
            binding.videoView.setVideoURI(Uri.parse(videoUrl))
        }

        fun onRecycled() {
            videoJob?.cancel()
        }
    }
}