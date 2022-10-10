package com.demo.browseseaching.server

import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.ServerBean
import com.demo.browseseaching.interfaces.IServerConnectCallback
import com.github.shadowsocks.Core
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.preference.DataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ServerConnectManager:ShadowsocksConnection.Callback {
    private var context:BasePage?=null
    private var state = BaseService.State.Idle
    private val sc= ShadowsocksConnection(true)
    private var currentServer=ServerInfoManager.createFastServer()
    private var iServerConnectCallback: IServerConnectCallback?=null


    fun init(context:BasePage,iServerConnectCallback: IServerConnectCallback){
        this.context=context
        this.iServerConnectCallback=iServerConnectCallback
        sc.connect(context,this)
    }

    fun connect(){
        state= BaseService.State.Connecting
        GlobalScope.launch {
            if (currentServer.isFastServer()){
                DataStore.profileId = ServerInfoManager.getRandomServer().getServerID()
            }else{
                DataStore.profileId = currentServer.getServerID()
            }
            Core.startService()
        }
        ConnectTimeManager.resetTime()
    }

    fun disconnect(){
        state= BaseService.State.Stopping
        GlobalScope.launch {
            Core.stopService()
        }
    }

    fun connected()= state==BaseService.State.Connected

    fun disconnected()= state==BaseService.State.Stopped

    fun getCurrentServer()= currentServer

    fun setCurrentServer(serverBean: ServerBean){
        currentServer=serverBean
    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) {
        this.state=state
        if (disconnected()){
            ConnectTimeManager.stop()
            iServerConnectCallback?.disconnectSuccess()
        }

        if (connected()){
            ConnectTimeManager.start()
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        val state = BaseService.State.values()[service.state]
        this.state=state
        if (connected()){
            ConnectTimeManager.start()
            iServerConnectCallback?.connectSuccess()
        }
    }


    override fun onBinderDied() {
        context?.let {
            sc.disconnect(it)
        }
    }

    fun onDestroy(){
        onBinderDied()
        context=null
        iServerConnectCallback=null
    }
}