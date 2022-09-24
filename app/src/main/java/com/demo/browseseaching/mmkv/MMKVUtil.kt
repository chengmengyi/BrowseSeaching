package com.demo.browseseaching.mmkv

import com.tencent.mmkv.MMKV

object MMKVUtil {

    fun writeInt(key:String,value: Int){
        MMKV.defaultMMKV().encode(key,value)
    }

    fun read(key:String) = MMKV.defaultMMKV().decodeInt(key,-1)
}