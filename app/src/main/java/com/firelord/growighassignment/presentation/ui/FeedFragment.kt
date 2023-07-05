package com.firelord.growighassignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.fragment.app.Fragment
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private lateinit var feedBinding: FragmentFeedBinding
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


    }
}