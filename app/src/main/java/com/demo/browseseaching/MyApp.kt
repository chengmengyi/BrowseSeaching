package com.demo.browseseaching

import android.app.Application
import com.demo.browseseaching.manager.SearchEngineManager
import com.tencent.mmkv.MMKV
import org.litepal.LitePal

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        LitePal.initialize(this)
        SearchEngineManager.init()
    }
}