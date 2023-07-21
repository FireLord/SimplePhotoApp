package com.firelord.growighassignment.presentation.ui.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentUploadVideoBinding

class UploadVideoFragment : Fragment() {
    private lateinit var uploadVideoBinding: FragmentUploadVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        uploadVideoBinding = FragmentUploadVideoBinding.inflate(inflater)
        return uploadVideoBinding.root
    }
}