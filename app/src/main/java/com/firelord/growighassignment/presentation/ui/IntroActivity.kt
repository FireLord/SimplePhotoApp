package com.firelord.growighassignment.presentation.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.firelord.growighassignment.R
import com.firelord.growighassignment.databinding.ActivityIntroBinding
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModel
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: GrowignViewModelFactory
    private lateinit var introBinding: ActivityIntroBinding
    private lateinit var viewModel: GrowignViewModel
    private lateinit var detector: GestureDetectorCompat
    private var newProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        viewModel = ViewModelProvider(this,factory)[GrowignViewModel::class.java]
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        detector = GestureDetectorCompat(this, OnBoardingListener())

        newProgress = 50
        viewModel.indicator.value = newProgress
        introBinding.progressBar.progress = newProgress

        introBinding.progressBar.setOnClickListener {
            newProgress += 25
            changeValue(newProgress)
        }

        viewModel.indicator.observe(this){
            when(it){
                50 -> {
                    // Set these from code here if user swipes back to initial point
                    introBinding.progressBar.progress = it
                    introBinding.imageView.setImageResource(R.drawable.intro1)
                    introBinding.textView.text = "About Us"
                    introBinding.ivButton.setImageResource(R.drawable.intro_arrow)
                }
                75 -> {
                    introBinding.progressBar.progress = it
                    introBinding.imageView.setImageResource(R.drawable.intro2)
                    introBinding.textView.text = "Our Mission"
                    introBinding.ivButton.setImageResource(R.drawable.intro_arrow)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event!!)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    private fun openDashboardActivity(){
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun changeValue(newProgress:Int) {
        val animator = ObjectAnimator.ofInt(introBinding.progressBar, "progress", newProgress)
        animator.duration = 1000
        animator.start()
        // Set things matching with above delay due to animation
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            viewModel.indicator.value = newProgress
        }
    }

    private fun onSwipeRight() {
        // Don't go below 50, bcz it will make progressBar 0 without action
        if (newProgress!=50){
            newProgress-=25
            changeValue(newProgress)
        }
    }

    private fun onLeftSwipe() {
        // Don't go above 100, bcz it will open the feed without button press
        if (newProgress!=100){
            newProgress+=25
            changeValue(newProgress)
        }
    }

    inner class OnBoardingListener : GestureDetector.SimpleOnGestureListener() {

        private val  SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            downEvent: MotionEvent,
            moveEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = moveEvent.x.minus(downEvent.x)
            val diffY = moveEvent.y.minus(downEvent.y)

            return if (Math.abs(diffX) > Math.abs(diffY)) {
                // this is a left or right swipe
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0 ) {
                        // right swipe
                        this@IntroActivity.onSwipeRight()
                    } else {
                        // left swipe.
                        this@IntroActivity.onLeftSwipe()
                    }
                    true
                } else  {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            } else {
                super.onFling(downEvent, moveEvent, velocityX, velocityY)
            }
        }
    }
}