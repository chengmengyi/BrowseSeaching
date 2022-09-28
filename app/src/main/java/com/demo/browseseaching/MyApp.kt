package com.demo.browseseaching

import android.app.Application
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.manager.SearchEngineManager
import com.demo.browseseaching.point.PointCommonUtil
import com.demo.browseseaching.point.PointUtil
import com.tencent.mmkv.MMKV
import org.litepal.LitePal

lateinit var mMyApp: MyApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        mMyApp=this
        MMKV.initialize(this)
        LitePal.initialize(this)
        SearchEngineManager.init()
        ReadFirebase.readFireBase()
        PointUtil.uploadTBA(this)
    }
}