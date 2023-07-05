package com.firelord.growighassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var welcomeBinding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        welcomeBinding = FragmentWelcomeBinding.inflate(inflater)
        return welcomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeBinding.btFeed.setOnClickListener {
            welcomeBinding.root.findNavController().navigate(R.id.action_welcomeFragment_to_feedFragment)
        }

        welcomeBinding.btUpload.setOnClickListener {
            welcomeBinding.root.findNavController().navigate(R.id.action_welcomeFragment_to_uploadFragment)
        }
    }
}