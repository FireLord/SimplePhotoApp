package com.firelord.growighassignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firelord.growighassignment.databinding.CommentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var commentBottomSheetBinding: CommentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        commentBottomSheetBinding = CommentBottomSheetBinding.inflate(inflater)
        return commentBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentBottomSheetBinding.textInputLayout.setEndIconOnClickListener {
            dismiss();
            Toast.makeText(requireContext(),"Comment Added",Toast.LENGTH_SHORT).show()
        }
    }
}