package com.demo.browseseaching.config

import com.demo.browseseaching.bean.ServerBean
import com.demo.browseseaching.util.createNumKey
import com.tencent.mmkv.MMKV
import org.json.JSONObject
import java.lang.Exception

object ReadFirebase {
    private var maxShowNum=50
    private var maxClickNum=15

    private var showedNum=0
    private var clickedNum=0
    
    val city= arrayListOf<String>()
    val serverList= arrayListOf<ServerBean>()

    fun readFireBase(){
        readLocalNum()
        createServerId(Config.localServerList)
//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful){
//                readAdConf(remoteConfig.getString("ad"))
//                readServer(remoteConfig.getString("flower_server"))
//                readCity(remoteConfig.getString("flower_city"))
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

    private fun readServer(string: String){
        try {
            val jsonArray = JSONObject(string).getJSONArray("flower_server")
            for (index in 0 until jsonArray.length()){
                val json0914Object = jsonArray.getJSONObject(index)
                serverList.add(
                    ServerBean(
                        json0914Object.optString("host"),
                        json0914Object.optInt("port"),
                        json0914Object.optString("pwd"),
                        json0914Object.optString("country"),
                        json0914Object.optString("city"),
                        json0914Object.optString("method"),
                    )
                )
            }
            createServerId(serverList)
        }catch (e:Exception){

        }
    }

    private fun readCity(string: String){
        try {
            val jsonArray = JSONObject(string).getJSONArray("flower_city")
            for (index in 0 until jsonArray.length()){
                city.add(jsonArray.optString(index))
            }
        }catch (e:Exception){

        }
    }

    private fun createServerId(list: ArrayList<ServerBean>) {
        list.forEach { it.createProfile() }
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