package com.firelord.growighassignment.presentation.ui.feed

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.firelord.growighassignment.data.db.Comment
import com.firelord.growighassignment.data.db.CommentDatabase
import com.firelord.growighassignment.databinding.CommentBottomSheetBinding
import com.firelord.growighassignment.databinding.FeedCommentBottomSheetBinding
import com.firelord.growighassignment.domain.repository.CommentRepository
import com.firelord.growighassignment.presentation.ui.DashboardActivity
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedCommentSheetFragment(private val repository: CommentRepository): BottomSheetDialogFragment() {
    private lateinit var feedCommentBottomSheetBinding: FeedCommentBottomSheetBinding
    private lateinit var viewModel: GrowignViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedCommentBottomSheetBinding = FeedCommentBottomSheetBinding.inflate(inflater)
        return feedCommentBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DashboardActivity).viewModel

        feedCommentBottomSheetBinding.textInputLayout.setEndIconOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                repository.insert(Comment(0,
                    feedCommentBottomSheetBinding.textInputLayout.editText?.text.toString()
                ))
            }
            dismiss();
            Toast.makeText(requireContext(),"Comment Added", Toast.LENGTH_SHORT).show()
        }
    }
}