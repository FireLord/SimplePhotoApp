package com.firelord.growighassignment.presentation.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firelord.growighassignment.databinding.CommentBottomSheetBinding
import com.firelord.growighassignment.databinding.FeedCommentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FeedCommentSheetFragment: BottomSheetDialogFragment() {
    private lateinit var feedCommentBottomSheetBinding: FeedCommentBottomSheetBinding
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
        feedCommentBottomSheetBinding.textInputLayout.setEndIconOnClickListener {
            dismiss();
            Toast.makeText(requireContext(),"Comment Added", Toast.LENGTH_SHORT).show()
        }
    }
}