package com.firelord.growighassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firelord.growighassignment.databinding.ActivityDashboardBinding
import com.firelord.growighassignment.viewmodel.GrowignViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var dashboardBinding: ActivityDashboardBinding
    lateinit var viewModel: GrowignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)
        viewModel = ViewModelProvider(this)[GrowignViewModel::class.java]
    }
}