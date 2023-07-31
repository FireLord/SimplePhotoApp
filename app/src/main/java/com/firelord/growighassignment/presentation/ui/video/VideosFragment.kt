package com.firelord.growighassignment.presentation.ui.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.firelord.growighassignment.R
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
        hideSoftKeyboard()
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
        videoAdapter.setOnUploadItemClickListener {position ->
            videosBinding.root.findNavController().navigate(R.id.action_videosFragment_to_uploadVideoFragment)
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
    private fun hideSoftKeyboard() {
        // Close the soft keyboard which was opened in home editText
        // explained here https://rmirabelle.medium.com/close-hide-the-soft-keyboard-in-android-db1da22b09d2
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}