package com.demo.browseseaching.bean

import org.litepal.crud.LitePalSupport

class HistoryBean(
    val title:String="",
    val webUrl:String="",
    val iconUrl:String="",
    var time:Long=0,
    val itemType:Int=10
): LitePalSupport(){
    override fun toString(): String {
        return "HistoryBean(title='$title', webUrl='$webUrl', iconUrl='$iconUrl', time=$time, itemType=$itemType)"
    }
}