package com.firelord.growighassignment.presentation.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.ActivityDashboardBinding
import com.firelord.growighassignment.presentation.adapter.CommentAdapter
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.firelord.growighassignment.presentation.adapter.VideoAdapter
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: GrowignViewModelFactory
    @Inject
    lateinit var photosAdapter: PhotosAdapter
    @Inject
    lateinit var videoAdapter: VideoAdapter
    @Inject
    lateinit var commentAdapter: CommentAdapter
    private lateinit var dashboardBinding: ActivityDashboardBinding
    lateinit var viewModel: GrowignViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)
        viewModel = ViewModelProvider(this,factory)[GrowignViewModel::class.java]

        viewModel.onFeedFrag.observe(this){
            if (it){
                setStatusBarGradiant(this)
            }else
            {
                this.window.statusBarColor = Color.WHITE
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        dashboardBinding.bnvFeed.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.feedFragment) {
                dashboardBinding.bnvFeed.visibility = View.VISIBLE
            }
            else {
                dashboardBinding.bnvFeed.visibility = View.GONE
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if the current destination is not the WelcomeFragment
                if (navController.currentDestination?.id != R.id.welcomeFragment) {
                    // Navigate back to the WelcomeFragment
                    navController.navigate(R.id.welcomeFragment)
                } else {
                    // If the current destination is the WelcomeFragment,
                    // allow the default back button behavior to be executed
                    isEnabled = false
                    finish()
                }
            }
        }
        // Add the onBackPressedCallback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setStatusBarGradiant(activity: Activity) {
        val window: Window = activity.window
        val background = ContextCompat.getDrawable(activity, R.drawable.gradient_background)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(activity,android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}