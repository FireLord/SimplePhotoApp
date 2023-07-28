package com.firelord.growighassignment.presentation.ui

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

        viewModel.indicator.value = 50

        introBinding.progressBar.setOnClickListener {
            viewModel.indicator.value = viewModel.indicator.value!! + 25
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