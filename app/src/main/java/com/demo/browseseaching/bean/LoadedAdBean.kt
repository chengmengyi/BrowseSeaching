package com.demo.browseseaching.bean

class LoadedAdBean(
    val loadTime:Long=0L,
    val loadAdData:Any?=null,
    val failMsg:String=""
) {

    fun checkEx()=(System.currentTimeMillis() - loadTime) >=1000L*3600L
}