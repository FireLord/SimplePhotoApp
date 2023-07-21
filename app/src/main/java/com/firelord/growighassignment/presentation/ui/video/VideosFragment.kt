package com.firelord.growighassignment.presentation.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.firelord.growighassignment.data.VideoItemList
import com.firelord.growighassignment.databinding.FragmentVideosBinding
import com.firelord.growighassignment.presentation.adapter.VideoAdapter
import com.firelord.growighassignment.presentation.ui.DashboardActivity

class VideosFragment : Fragment() {
    private lateinit var videosBinding: FragmentVideosBinding
    private lateinit var videoAdapter: VideoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        videosBinding = FragmentVideosBinding.inflate(inflater)
        return videosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoAdapter = (activity as DashboardActivity).videoAdapter

        videosBinding.viewPager2.adapter = videoAdapter
        videosBinding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        val videoItemList = VideoItemList.videoItem
        videoAdapter.setList(videoItemList)

        videoAdapter.setOnItemClickListener { position ->
            // Open the bottom sheet here
            openBottomSheet(position)
        }

        videoAdapter.setOnDotItemClickListener {position ->
            openDotBottomSheet(position)
        }
    }

    private fun openBottomSheet(position: Int) {
        val bottomSheetFragment = CommentBottomSheetFragment()
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }
    private fun openDotBottomSheet(position: Int) {
        val bottomSheetFragment = DotBottomSheetFragment()
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }
}