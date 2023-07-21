package com.firelord.growighassignment.presentation.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firelord.growighassignment.data.db.CommentDatabase
import com.firelord.growighassignment.databinding.FragmentCommentBinding
import com.firelord.growighassignment.domain.repository.CommentRepository
import com.firelord.growighassignment.presentation.adapter.CommentAdapter
import com.firelord.growighassignment.presentation.ui.DashboardActivity

class CommentFragment() : Fragment() {
    private lateinit var commentBinding: FragmentCommentBinding
    private lateinit var commentAdapter: CommentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        commentBinding = FragmentCommentBinding.inflate(inflater)
        return commentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentAdapter = (activity as DashboardActivity).commentAdapter
        initRecyclerView()
    }

    private fun initRecyclerView(){
        commentBinding.rvComment.adapter = commentAdapter
        commentBinding.rvComment.layoutManager = LinearLayoutManager(activity)
        val dao = CommentDatabase.getInstance(requireContext()).commentDao
        CommentRepository(dao).comments.observe(viewLifecycleOwner){
            commentAdapter.setList(it)
            commentAdapter.notifyDataSetChanged()
        }
    }
}