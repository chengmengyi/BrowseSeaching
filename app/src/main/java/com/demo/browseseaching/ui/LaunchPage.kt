package com.demo.browseseaching.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ActivityUtils
import com.demo.browseseaching.R
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.LoadAdManager
import com.demo.browseseaching.admob.ShowFullAd
import com.demo.browseseaching.base.BasePage
import kotlinx.android.synthetic.main.activity_main.*

class LaunchPage : BasePage(R.layout.activity_main) {
    private var valueAnimator:ValueAnimator?=null
    private val showAd by lazy { ShowFullAd(this,AdType.AD_TYPE_OPEN){ toHome()} }

    override fun initView() {
        LoadAdManager.check(AdType.AD_TYPE_OPEN)
        startAnimator()
    }


    private fun startAnimator(){
        valueAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration = 10000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Int
                launch_progress.progress = progress
                val du = (10 * (progress / 100.0F)).toInt()
                if (du in 2..9){
                    showAd.checkShow { b->
                        stopAnimator()
                        launch_progress.progress = 100
                        if (b){
                            toHome()
                        }
                    }
                }else if (du>=10){
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