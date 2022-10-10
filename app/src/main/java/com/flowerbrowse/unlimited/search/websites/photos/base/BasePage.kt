package com.flowerbrowse.unlimited.search.websites.photos.base

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus

abstract class BasePage(private val layoutId:Int):AppCompatActivity() {
    protected lateinit var immersionBar: ImmersionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        height()
        setContentView(layoutId)
        immersionBar=ImmersionBar.with(this).apply {
            statusBarAlpha(0f)
            autoDarkModeEnable(true)
            statusBarDarkFont(false)
            init()
        }

        if (initEventBus()){
            EventBus.getDefault().register(this)
        }

        initView()
    }

    abstract fun initView()

    protected open fun initEventBus():Boolean=false

    fun height(){
        val metrics: DisplayMetrics = resources.displayMetrics
        val td = metrics.heightPixels / 760f
        val dpi = (160 * td).toInt()
        metrics.density = td
        metrics.scaledDensity = td
        metrics.densityDpi = dpi
    }

    override fun onDestroy() {
        super.onDestroy()
        if (initEventBus()){
            EventBus.getDefault().unregister(this)
        }
    }
}