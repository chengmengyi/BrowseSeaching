package com.demo.browseseaching.server

import com.demo.browseseaching.interfaces.IConnectTimeCallback
import kotlinx.coroutines.*
import java.lang.Exception

object ConnectTimeManager {
    private var time = 0L
    private var timeJob:Job?=null
    private val callback= arrayListOf<IConnectTimeCallback>()

    fun resetTime(){
        time=0L
    }

    fun start(){
        if (null!= timeJob) return
        timeJob = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                callback.forEach {
                    it.connectTime(trans())
                }
                time++
                delay(1000L)
            }
        }
    }

    fun stop(){
        timeJob?.cancel()
        timeJob=null
    }

    fun getCurrentTime()= trans()

    fun addCallback(iConnectTimeCallback: IConnectTimeCallback){
        callback.add(iConnectTimeCallback)
    }

    fun removeCallback(iConnectTimeCallback: IConnectTimeCallback){
        callback.remove(iConnectTimeCallback)
    }

    private fun trans():String{
        try {
            val shi= time/3600
            val fen= (time % 3600) / 60
            val miao= (time % 3600) % 60
            val s=if (shi<10) "0${shi}" else shi
            val f=if (fen<10) "0${fen}" else fen
            val m=if (miao<10) "0${miao}" else miao
            return "${s}:${f}:${m}"
        }catch (e: Exception){}
        return "00:00:00"
    }
}