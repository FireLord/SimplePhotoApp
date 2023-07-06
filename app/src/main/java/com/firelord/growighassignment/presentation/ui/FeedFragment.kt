package com.firelord.growighassignment.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firelord.growighassignment.data.model.Urls
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.databinding.FragmentFeedBinding
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import com.google.android.material.snackbar.Snackbar

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
        return feedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DashboardActivity).viewModel
        photosAdapter = (activity as DashboardActivity).photosAdapter
        initRecyclerView()
        viewPhotoList()

        viewModel.pageNum.value=1
        feedBinding.swipeRefreshLayout.setOnRefreshListener{
            // on every refresh load next page and show its 10 photos
            viewModel.pageNum.value = viewModel.pageNum.value!! + 1
            viewPhotoList()
        }
    }

    private fun initRecyclerView(){
        feedBinding.rvFeed.adapter = photosAdapter
        feedBinding.rvFeed.layoutManager = LinearLayoutManager(activity)
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