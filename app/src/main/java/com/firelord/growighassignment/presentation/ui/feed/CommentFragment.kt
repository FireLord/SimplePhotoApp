package com.firelord.growighassignment.presentation.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firelord.growighassignment.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {
    private lateinit var commentBinding: FragmentCommentBinding
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

    }
}