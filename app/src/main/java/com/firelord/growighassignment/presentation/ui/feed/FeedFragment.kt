package com.firelord.growighassignment.presentation.ui.feed

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.firelord.growighassignment.R
import com.firelord.growighassignment.data.db.CommentDatabase
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.databinding.FragmentFeedBinding
import com.firelord.growighassignment.domain.repository.CommentRepository
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.firelord.growighassignment.presentation.ui.DashboardActivity
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        photosAdapter.setOnItemClickListener { position ->
            // Open the bottom sheet here
            openBottomSheet(position)
        }
        photosAdapter.setOnCommentItemClickListener { position ->
            openCommentFragment(position)
        }

        initRecyclerView()
        viewPhotoList()
        viewModel.onFeedFrag.value = true

        viewModel.pageNum.value=1
        feedBinding.swipeRefreshLayout.setOnRefreshListener{
            // on every refresh load next page and show its 10 photos
            viewModel.pageNum.value = viewModel.pageNum.value!! + 1
            viewPhotoList()
        }

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        feedBinding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            coroutineScope.coroutineContext.cancelChildren()

            if (scrollY > oldScrollY && feedBinding.fabUpload.isShown) {
                feedBinding.fabUpload.hide()
            } else if (scrollY < oldScrollY && !feedBinding.fabUpload.isShown) {
                feedBinding.fabUpload.show()
            }

            // Show fab when scroll is stopped
            coroutineScope.launch {
                delay(500)
                feedBinding.fabUpload.show()
            }
        })

        feedBinding.fabUpload.setOnClickListener {
            it.findNavController().navigate(R.id.action_feedFragment_to_uploadFragment)
        }
    }
    private fun openBottomSheet(position: Int) {
        val dao = CommentDatabase.getInstance(requireContext()).commentDao
        val bottomSheetFragment = FeedCommentSheetFragment(CommentRepository(dao))
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun openCommentFragment(position: Int) {
        feedBinding.root.findNavController().navigate(R.id.action_feedFragment_to_commentFragment)
    }

    private fun initRecyclerView(){
        feedBinding.rvFeed.adapter = photosAdapter
        feedBinding.rvFeed.layoutManager = LinearLayoutManager(activity)
        val itemTouchHelper = ItemTouchHelper(SwipeToShareCallback(photosAdapter,requireContext()))
        itemTouchHelper.attachToRecyclerView(feedBinding.rvFeed)
        ViewCompat.setNestedScrollingEnabled(feedBinding.rvFeed, false);
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