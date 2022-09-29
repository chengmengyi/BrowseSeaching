package com.demo.browseseaching.bean

import org.litepal.crud.LitePalSupport

class BookmarkBean(
    val title:String="",
    val webUrl:String="",
    val iconUrl:String="",
    var time:Long=0,
    var isOpen:Boolean=false,
): LitePalSupport(){

}