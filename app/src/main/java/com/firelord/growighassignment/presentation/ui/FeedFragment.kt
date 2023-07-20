package com.firelord.growighassignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.R
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.databinding.FragmentFeedBinding
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel


class FeedFragment : Fragment() {

    private lateinit var feedBinding: FragmentFeedBinding
    private lateinit var viewModel: GrowignViewModel
    private lateinit var photosAdapter: PhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        feedBinding = FragmentFeedBinding.inflate(inflater)
        context?.theme?.applyStyle(R.style.Feed_Fragment_StatusBar, true)
        return feedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DashboardActivity).viewModel
        photosAdapter = (activity as DashboardActivity).photosAdapter
        initRecyclerView()
        viewPhotoList()
        viewModel.onFeedFrag.value = true

        viewModel.pageNum.value=1
        feedBinding.swipeRefreshLayout.setOnRefreshListener{
            // on every refresh load next page and show its 10 photos
            viewModel.pageNum.value = viewModel.pageNum.value!! + 1
            viewPhotoList()
        }

        feedBinding.rvFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && feedBinding.fabUpload.isShown) feedBinding.fabUpload.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) feedBinding.fabUpload.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        feedBinding.fabUpload.setOnClickListener {
            it.findNavController().navigate(R.id.action_feedFragment_to_uploadFragment)
        }
    }

    private fun initRecyclerView(){
        feedBinding.rvFeed.adapter = photosAdapter
        feedBinding.rvFeed.layoutManager = LinearLayoutManager(activity)
        val itemTouchHelper = ItemTouchHelper(SwipeToShareCallback(photosAdapter,requireContext()))
        itemTouchHelper.attachToRecyclerView(feedBinding.rvFeed)
    }

    private fun viewPhotoList(){
        viewModel.pageNum.observe(viewLifecycleOwner){pageNum ->
            viewModel.getPhotos(pageNum)
        }
        viewModel.photos.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        feedBinding.swipeRefreshLayout.isRefreshing = false
                        photosAdapter.setList(it)
                        photosAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        feedBinding.swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(activity,"An error occurred: $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    feedBinding.swipeRefreshLayout.isRefreshing = true
                }
            }
        }
    }
}