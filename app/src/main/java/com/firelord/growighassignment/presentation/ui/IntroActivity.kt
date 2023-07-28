package com.firelord.growighassignment.presentation.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.ActivityIntroBinding
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: GrowignViewModelFactory
    private lateinit var introBinding: ActivityIntroBinding
    private lateinit var viewModel: GrowignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        viewModel = ViewModelProvider(this,factory)[GrowignViewModel::class.java]
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        var newProgress = 50
        viewModel.indicator.value = newProgress
        introBinding.progressBar.progress = newProgress

        introBinding.progressBar.setOnClickListener {
            newProgress += 25
            val animator = ObjectAnimator.ofInt(introBinding.progressBar, "progress", newProgress)
            animator.duration = 1000
            animator.start()
            // Set things matching with above delay due to animation
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                viewModel.indicator.value = newProgress
            }
        }

        viewModel.indicator.observe(this){
            when(it){
                75 -> {
                    introBinding.progressBar.progress = it
                    introBinding.imageView.setImageResource(R.drawable.intro2)
                    introBinding.textView.text = "Our Mission"
                }
                100 -> {
                    introBinding.progressBar.progress = it
                    introBinding.imageView.setImageResource(R.drawable.intro3)
                    introBinding.textView.text = "Our Vission"
                    introBinding.ivButton.setImageResource(R.drawable.ready_button)
                }
                125 -> {
                    sharedPreferences.edit().putBoolean("activityOpen", true).apply()
                    openDashboardActivity()
                }
            }
        }

        introBinding.button.setOnClickListener {
            sharedPreferences.edit().putBoolean("activityOpen", true).apply()
            openDashboardActivity()
        }
    }

    private fun openDashboardActivity(){
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}