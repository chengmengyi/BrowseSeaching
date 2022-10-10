package com.demo.browseseaching.ui.server

import com.demo.browseseaching.R
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.ShowNativeAd
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.interfaces.IConnectTimeCallback
import com.demo.browseseaching.server.ConnectTimeManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultPage:BasePage(R.layout.activity_result), IConnectTimeCallback {
    private var connect=false
    private val showAd by lazy {  ShowNativeAd(this,AdType.AD_TYPE_CONNECT_RESULT) }

    override fun initView() {
        immersionBar.statusBarView(top).init()
        iv_back.setOnClickListener { finish() }

        connect=intent.getBooleanExtra("connect",false)
        tv_connect_time.isSelected=connect
        if (connect){
            tv_title.text="Connect success"
            iv_connect.setImageResource(R.drawable.result1)
            ConnectTimeManager.addCallback(this)
        }else{
            tv_title.text="Disconnected"
            iv_connect.setImageResource(R.drawable.result2)
            tv_connect_time.text=ConnectTimeManager.getCurrentTime()
        }
    }

    override fun connectTime(time: String) {
        tv_connect_time.text=time
    }

    override fun onResume() {
        super.onResume()
        showAd.checkHasAdRes()
    }

    override fun onDestroy() {
        super.onDestroy()
        showAd.stopCheck()
        if (connect){
            ConnectTimeManager.removeCallback(this)
        }
    }
}