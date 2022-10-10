package com.flowerbrowse.unlimited.search.websites.photos.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ActivityUtils
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import kotlinx.android.synthetic.main.activity_main.*

class LaunchPage : BasePage(R.layout.activity_main) {
    private var valueAnimator:ValueAnimator?=null

    override fun initView() {
        startAnimator()
    }


    private fun startAnimator(){
        valueAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration = 1000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Int
                launch_progress.progress = progress
//                val du = (10 * (progress / 100.0F)).toInt()
                if (progress >= 100) {
                    stopAnimator()
                    toHome()
                }
            }

            start()
        }
    }

    private fun toHome(){
        val activityExistsInStack = ActivityUtils.isActivityExistsInStack(HomePage::class.java)
        if (!activityExistsInStack){
            startActivity(Intent(this,HomePage::class.java))
        }
        finish()
    }

    private fun stopAnimator(){
        valueAnimator?.cancel()
        valueAnimator=null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            return true
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        valueAnimator?.resume()
    }

    override fun onPause() {
        super.onPause()
        valueAnimator?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAnimator()
    }
}