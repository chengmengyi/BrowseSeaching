package com.demo.browseseaching.bean

import org.litepal.crud.LitePalSupport

class ReadLaterBean(
    val title:String="",
    val webUrl:String="",
    val iconUrl:String="",
    var time:Long=0
): LitePalSupport(){

}