package com.firelord.growighassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {

    private lateinit var uploadBinding: FragmentUploadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        uploadBinding = FragmentUploadBinding.inflate(inflater)
        return uploadBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}