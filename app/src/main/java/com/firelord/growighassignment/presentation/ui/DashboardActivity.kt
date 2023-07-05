package com.firelord.growighassignment.presentation.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.ActivityDashboardBinding
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var dashboardBinding: ActivityDashboardBinding
    lateinit var viewModel: GrowignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)
        viewModel = ViewModelProvider(this)[GrowignViewModel::class.java]

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val activityOpen = sharedPreferences.getBoolean("activityOpen", false)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        if (activityOpen){
            navController.navigate(R.id.action_introFragment_to_welcomeFragment)
        }
    }
}