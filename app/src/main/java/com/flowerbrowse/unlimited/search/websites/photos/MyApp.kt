package com.flowerbrowse.unlimited.search.websites.photos

import android.app.Application
import com.flowerbrowse.unlimited.search.websites.photos.manager.SearchEngineManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tencent.mmkv.MMKV
import org.litepal.LitePal

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        LitePal.initialize(this)
        Firebase.initialize(this)
        SearchEngineManager.init()
    }
}