package com.firelord.growighassignment.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.R
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


class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val videoList = ArrayList<VideoItem>()
    private var onItemClickListener: ((position: Int) -> Unit)? = null
    private var onDotItemClickListener: ((position: Int) -> Unit)? = null
    private var onUploadItemClickListener: ((position: Int) -> Unit)? = null

    fun setList(videoItem: List<VideoItem>){
        videoList.clear()
        videoList.addAll(videoItem)
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setOnDotItemClickListener(listener: (position: Int) -> Unit) {
        this.onDotItemClickListener = listener
    }

    fun setOnUploadItemClickListener(listener: (position: Int) -> Unit) {
        this.onUploadItemClickListener = listener
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

    inner class VideoViewHolder(val binding:VideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        private var videoJob: Job? = null
        var isLiked = false
        var isVideoPlaying = false
        fun bind(videoItem: VideoItem){
            videoJob?.cancel()
            binding.progressBarVideo.visibility = View.VISIBLE
            makeItemInvisible()
            binding.fabShare.setOnClickListener {
                shareVideoUrl(videoItem.url, it.context)
            }

            binding.fabComment.setOnClickListener {
                onItemClickListener?.invoke(position)
            }
            binding.fabSettings.setOnClickListener {
                onDotItemClickListener?.invoke(position)
            }
            binding.ivUploadVideo.setOnClickListener {
                onUploadItemClickListener?.invoke(position)
            }

            binding.fabLike.setOnClickListener {
                if (isLiked) {
                    // If already liked, remove the like
                    isLiked = false
                    binding.fabLike.setImageResource(R.drawable.ic_heart)
                } else {
                    // If not liked, add the like and remove the dislike
                    isLiked = true
                    binding.fabLike.setImageResource(R.drawable.ic_heart_filled)
                }
            }

            videoJob = CoroutineScope(IO).launch {
                    try {
                        val request = YoutubeDLRequest(videoItem.url)
                        request.addOption("-f", "best")
                        val streamInfo = YoutubeDL.getInstance().getInfo(request)

                        withContext(Dispatchers.Main) {
                            setupVideoView(streamInfo.url)
                            binding.progressBarVideo.visibility = View.GONE
                            makeItemVisible()

                            binding.tvVideoTitle.text = streamInfo.title
                            binding.tvProfileName.text = streamInfo.uploader
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
                it.isLooping = true
                // Add OnClickListener to toggle play and pause
                binding.videoView.setOnClickListener {
                    if (isVideoPlaying) {
                        binding.videoView.pause()
                    } else {
                        binding.videoView.start()
                    }
                    isVideoPlaying = !isVideoPlaying
                }
            }
            binding.videoView.setVideoURI(Uri.parse(videoUrl))
        }

        fun onRecycled() {
            videoJob?.cancel()
        }
        fun shareVideoUrl(videoUrl: String, context: Context) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrl)
            context.startActivity(Intent.createChooser(shareIntent, "Share Video"))
        }
        fun makeItemVisible(){
            binding.tvVideoTitle.visibility = View.VISIBLE
            binding.fabLike.visibility = View.VISIBLE
            binding.fabComment.visibility = View.VISIBLE
            binding.fabShare.visibility = View.VISIBLE
            binding.fabSettings.visibility = View.VISIBLE
            binding.buFollow.visibility = View.VISIBLE
            binding.ivProfile.visibility = View.VISIBLE
            binding.tvProfileName.visibility = View.VISIBLE
            binding.ivUploadVideo.visibility = View.VISIBLE
        }
        fun makeItemInvisible(){
            binding.tvVideoTitle.visibility = View.GONE
            binding.fabLike.visibility = View.GONE
            binding.fabComment.visibility = View.GONE
            binding.fabShare.visibility = View.GONE
            binding.fabSettings.visibility = View.GONE
            binding.buFollow.visibility = View.GONE
            binding.ivProfile.visibility = View.GONE
            binding.tvProfileName.visibility = View.GONE
            binding.ivUploadVideo.visibility = View.GONE
        }
    }
}