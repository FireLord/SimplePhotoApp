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

class FeedFragment : Fragment() {

    private lateinit var feedBinding: FragmentFeedBinding
    private lateinit var viewModel: GrowignViewModel
    private lateinit var photosAdapter: PhotosAdapter
    private var page = 1
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
        initRecyclerView()
        viewPhotoList()
    }

    private fun initRecyclerView(){
        photosAdapter = PhotosAdapter()
        feedBinding.rvFeed.adapter = photosAdapter
        feedBinding.rvFeed.layoutManager = LinearLayoutManager(activity)
    }

    private fun viewPhotoList(){
        viewModel.getPhotos(page)
        viewModel.photos.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        photosAdapter.setList(it)
                        photosAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred: $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar(){
        feedBinding.progressBar3.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        feedBinding.progressBar3.visibility = View.INVISIBLE
    }
}