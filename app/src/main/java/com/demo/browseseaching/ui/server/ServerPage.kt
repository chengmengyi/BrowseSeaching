package com.demo.browseseaching.ui.server

import android.content.Intent
import android.net.VpnService
import androidx.appcompat.app.AlertDialog
import com.demo.browseseaching.R
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.LoadAdManager
import com.demo.browseseaching.admob.ShowFullAd
import com.demo.browseseaching.admob.ShowNativeAd
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.interfaces.IConnectTimeCallback
import com.demo.browseseaching.interfaces.IServerConnectCallback
import com.demo.browseseaching.server.ConnectTimeManager
import com.demo.browseseaching.server.ServerConnectManager
import com.demo.browseseaching.util.*
import com.github.shadowsocks.utils.StartService
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.coroutines.*

class ServerPage:BasePage(R.layout.activity_server), IConnectTimeCallback, IServerConnectCallback {
    private var click=true
    private var connect=true
    private var permission=false
    private var checkJob: Job?=null
    private val register=registerForActivityResult(StartService()) {
        if (!it && permission) {
            permission = false
            startConnectServer()
        } else {
            click=true
            toast("Connected fail")
        }
    }

    private val showConnectAd by lazy {  ShowFullAd(this,AdType.AD_TYPE_CONNECT){ jumpToResult() } }
    private val showNativeAd by lazy { ShowNativeAd(this,AdType.AD_TYPE_CONNECT_HOME) }

    override fun initView() {
        immersionBar.statusBarView(top).init()
        ServerConnectManager.init(this,this)
        ConnectTimeManager.addCallback(this)
        setClickListener()
    }

    private fun setClickListener(){
        iv_back.setOnClickListener {
            if (click){
                finish()
            }
        }
        iv_connect_server.setOnClickListener {
            if (click){
                click=false
                LoadAdManager.check(AdType.AD_TYPE_CONNECT)
                LoadAdManager.check(AdType.AD_TYPE_CONNECT_RESULT)
                val connected = ServerConnectManager.connected()
                preConnect(!connected)
            }
        }

        ll_choose_server.setOnClickListener {
            if (click){
                startActivityForResult(Intent(this,ChooseServerPage::class.java),300)
            }
        }
    }

    private fun preConnect(connect:Boolean){
        if (connect){
            startDisconnectServer()
        }else{
            updateServerInfo()
            if (getNetStatus()==1){
                AlertDialog.Builder(this).apply {
                    setMessage("You are not currently connected to the network")
                    setPositiveButton("sure", null)
                    show()
                }
                click=true
                return
            }
            if (VpnService.prepare(this) != null) {
                permission = true
                register.launch(null)
                return
            }
            startConnectServer()
        }
    }

    private fun startConnectServer(){
        updateConnectingUI()
        checkConnectResult(true)
    }

    private fun startDisconnectServer(){
        updateDisconnectingUI()
        checkConnectResult(false)
    }

    private fun checkConnectResult(connect: Boolean){
        this.connect=connect
        checkJob = GlobalScope.launch(Dispatchers.Main) {
            var time = 0
            while (true) {
                if (!isActive) {
                    break
                }
                delay(1000)
                time++
                if (time >= 10) {
                    cancel()
                    connectCompleted()
                }
                if (time==3){
                    if (connect){
                        ServerConnectManager.connect()
                    }else{
                        ServerConnectManager.disconnect()
                    }
                }
                if (time in 3..9){
                    if(connectOrDisconnectStatus()){
                        showConnectAd.checkShow { a->
                            cancel()
                            connectCompleted(jump = a)
                        }
                    }
                }
            }
        }
    }

    private fun connectOrDisconnectStatus()=if (connect) ServerConnectManager.connected() else ServerConnectManager.disconnected()

    private fun connectCompleted(jump:Boolean=true){
        if (connectOrDisconnectStatus()){
            if (connect){
                updateConnectedUI()
            }else{
                updateStoppedUI()
                updateServerInfo()
            }
            if (jump){
                jumpToResult()
            }
            click=true
        }else{
            updateStoppedUI()
            toast(if (connect) "Connect Fail" else "Disconnect Fail")
            click=true
        }
    }

    private fun jumpToResult(){
        if (ActivityLifecycle.front){
            startActivity(Intent(this,ResultPage::class.java).apply {
                putExtra("connect",connect)
            })
        }
    }

    private fun updateDisconnectingUI(){
        iv_connect_server.showInvisible(false)
        lottie_view.show(true)
        tv_connect_time.isSelected=false
        tv_connect_text.text="Connecting…"
        iv_connect_btn.setImageResource(R.drawable.server_btn)
        iv_connect_server.setImageResource(R.drawable.server_idle)
    }

    private fun updateConnectingUI(){
        iv_connect_server.showInvisible(false)
        lottie_view.show(false)
        tv_connect_text.text="Stopping…"
        tv_connect_time.isSelected=false
        iv_connect_btn.setImageResource(R.drawable.server_btn)
        iv_connect_server.setImageResource(R.drawable.server_idle)
    }

    private fun updateConnectedUI(){
        iv_connect_server.showInvisible(true)
        lottie_view.show(true)
        tv_connect_time.isSelected=true
        tv_connect_text.text="Disconnect"
        iv_connect_btn.setImageResource(R.drawable.server_connected_btn)
        iv_connect_server.setImageResource(R.drawable.server_connected)
    }

    private fun updateStoppedUI(){
        iv_connect_server.showInvisible(true)
        lottie_view.show(false)
        tv_connect_time.isSelected=false
        tv_connect_text.text="Tap To Connect"
        iv_connect_btn.setImageResource(R.drawable.server_btn)
        iv_connect_server.setImageResource(R.drawable.server_idle)
    }

    private fun updateServerInfo(){
        val country = ServerConnectManager.getCurrentServer().country
        tv_server_country.text=country
        iv_server_logo.setImageResource(getServerLogo(country))
    }

    override fun connectTime(time: String) {
        tv_connect_time.text=time
    }

    override fun connectSuccess() {
        updateConnectedUI()
    }

    override fun disconnectSuccess() {
        if (click){
            updateStoppedUI()
        }
    }

    override fun onResume() {
        super.onResume()
        if (ActivityLifecycle.refreshServerHomeAd){
            showNativeAd.checkHasAdRes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==300){
            when(data?.getStringExtra("action")){
                "connect"->{
                    updateServerInfo()
                    preConnect(true)
                }
                "disconnect"->{
                    preConnect(false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        checkJob?.cancel()
        checkJob=null
        showNativeAd.stopCheck()
        ServerConnectManager.onDestroy()
        ActivityLifecycle.refreshServerHomeAd=true
        ConnectTimeManager.removeCallback(this)
    }
}