package com.demo.browseseaching.config

import com.demo.browseseaching.util.createNumKey
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tencent.mmkv.MMKV
import org.json.JSONObject
import java.lang.Exception

object ReadFirebase {
    private var maxShowNum=50
    private var maxClickNum=15

    private var showedNum=0
    private var clickedNum=0

    fun readFireBase(){
        readLocalNum()

//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful){
//                readAdConf(remoteConfig.getString("ad"))
//            }
//        }
    }

    private fun readLocalNum(){
        showedNum=MMKV.defaultMMKV().decodeInt(createNumKey("showedNum"),0)
        clickedNum=MMKV.defaultMMKV().decodeInt(createNumKey("clickedNum"),0)
    }

    private fun readAdConf(string: String){
        MMKV.defaultMMKV().encode("ad",string)
        try {
            val jsonObject = JSONObject(string)
            maxClickNum=jsonObject.optInt("flower_click")
            maxShowNum=jsonObject.optInt("flower_show")
        }catch (e:Exception){

        }
    }

    fun getAdJson():String{
        val decodeString = MMKV.defaultMMKV().decodeString("ad", "")
        return if (decodeString.isNullOrEmpty()) Config.BROWSE_AD_CONF else decodeString
    }

    fun checkLoadAdIsLimit() = showedNum>= maxShowNum|| clickedNum>= maxClickNum

    fun addAdShowNum(){
        showedNum++
        saveNum("showedNum",showedNum)
    }

    fun addClickNum(){
        clickedNum++
        saveNum("clickedNum",clickedNum)
    }

    private fun saveNum(type:String,num:Int){
        MMKV.defaultMMKV().encode(createNumKey(type),num)
    }
}