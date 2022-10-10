package com.demo.browseseaching

import android.app.ActivityManager
import android.app.Application
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.manager.SearchEngineManager
import com.demo.browseseaching.point.PointCommonUtil
import com.demo.browseseaching.point.PointUtil
import com.demo.browseseaching.ui.HomePage
import com.demo.browseseaching.util.ActivityLifecycle
import com.github.shadowsocks.Core
import com.tencent.mmkv.MMKV
import org.litepal.LitePal

lateinit var mMyApp: MyApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        mMyApp=this
        Core.init(this,HomePage::class)
        MMKV.initialize(this)
        if (!packageName.equals(runningAppProcesses(this))){
            return
        }
        LitePal.initialize(this)
        SearchEngineManager.init()
        ActivityLifecycle.register(this)
        ReadFirebase.readFireBase()
        PointUtil.uploadTBA(this)
    }

    private fun runningAppProcesses(applicationContext: Application): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid === pid) {
                processName = process.processName
            }
        }
        return processName
    }
}