package com.demo.browseseaching.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.demo.browseseaching.ui.HomePage
import com.demo.browseseaching.ui.LaunchPage
import com.google.android.gms.ads.AdActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ActivityCallBack {
    private var background=false
    private var job: Job?=null


    fun register(application: Application){
        application.registerActivityLifecycleCallbacks(callback)
    }

    private val callback=object : Application.ActivityLifecycleCallbacks{
        private var acNum=0
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            acNum++
            stopJob()
            if (acNum==1){
                if (background){
                    if (ActivityUtils.isActivityExistsInStack(HomePage::class.java)){
                        activity.startActivity(Intent(activity, LaunchPage::class.java))
                    }
                }
                background=false
            }
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            acNum--
            if (acNum<=0){
                job= GlobalScope.launch {
                    delay(3000L)
                    background=true
                    ActivityUtils.finishActivity(LaunchPage::class.java)
                    ActivityUtils.finishActivity(AdActivity::class.java)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

        private fun stopJob(){
            job?.cancel()
            job=null
        }
    }
}