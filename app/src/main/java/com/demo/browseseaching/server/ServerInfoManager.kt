package com.demo.browseseaching.server

import com.demo.browseseaching.bean.ServerBean
import com.demo.browseseaching.config.Config
import com.demo.browseseaching.config.ReadFirebase

object ServerInfoManager {

    fun getServerList()=ReadFirebase.serverList.ifEmpty { Config.localServerList }

    fun createFastServer()=ServerBean(country = "Smart Servers")

    fun getRandomServer():ServerBean{
        val serverList = getServerList()
        if (!ReadFirebase.city.isNullOrEmpty()){
            val filter = serverList.filter { ReadFirebase.city.contains(it.city) }
            if (!filter.isNullOrEmpty()){
                return filter.random()
            }
        }
        return serverList.random()
    }
}