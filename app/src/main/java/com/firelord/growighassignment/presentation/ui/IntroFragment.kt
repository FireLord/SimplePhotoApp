package com.firelord.growighassignment.presentation.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.FragmentIntroBinding
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroFragment : Fragment() {

    private lateinit var introBinding: FragmentIntroBinding
    private lateinit var viewModel: GrowignViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        introBinding = FragmentIntroBinding.inflate(inflater)
        return introBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DashboardActivity).viewModel
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.edit().putBoolean("activityOpen", true).apply()

        viewModel.indicator.value = 50

        introBinding.progressBar.setOnClickListener {
            viewModel.indicator.value = viewModel.indicator.value!! + 25
        }

        viewModel.indicator.observe(viewLifecycleOwner){
            when(it){
                75 -> {
                    introBinding.progressBar.progress = 75
                    introBinding.imageView.setImageResource(R.drawable.intro2)
                    introBinding.textView.text = "Our Mission"
                }
                100 -> {
                    introBinding.progressBar.progress = 100
                    introBinding.imageView.setImageResource(R.drawable.intro3)
                    introBinding.textView.text = "Our Vission"
                }
                125 -> {
                    introBinding.root.findNavController().navigate(R.id.action_introFragment_to_welcomeFragment)
                }
            }
        }

        introBinding.button.setOnClickListener {
            introBinding.root.findNavController().navigate(R.id.action_introFragment_to_welcomeFragment)
        }
    }
}