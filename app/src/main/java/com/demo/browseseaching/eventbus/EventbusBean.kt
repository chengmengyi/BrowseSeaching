package com.demo.browseseaching.eventbus

import org.greenrobot.eventbus.EventBus

class EventbusBean(
    val code:Int,
    var str:String="",
    var boolean: Boolean=false,
    var any:Any?=null
) {

    fun send(){
        EventBus.getDefault().post(this)
    }
}