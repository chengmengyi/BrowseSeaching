package com.demo.browseseaching.admob

import com.demo.browseseaching.bean.ConfAdBean
import com.demo.browseseaching.bean.LoadedAdBean
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.util.printLog
import org.json.JSONObject

object LoadAdManager:LoadAdmob() {
    var isShowingFullAd=false
    private val isLoadingList= arrayListOf<String>()
    private val adResMap= hashMapOf<String,LoadedAdBean>()


    fun check(type:String, loadOpen: Boolean=true){
        if (checkIsLoading(type)){
            printLog("$type loading")
            return
        }

        if (hasAdRes(type)){
            printLog("$type has cache")
            return
        }

        if (ReadFirebase.checkLoadAdIsLimit()){
            printLog("load ad limit")
            return
        }
        val parseAdJson = parseAdJson(type)
        if (parseAdJson.isEmpty()){
            printLog("$type ad list empty")
            return
        }
        isLoadingList.add(type)
        startLoadAd(type,parseAdJson.iterator(),loadOpen)
    }

    private fun startLoadAd(type: String, iterator: Iterator<ConfAdBean>, loadOpen: Boolean) {
        val next = iterator.next()
        loadAd(type,next){
            if (null!=it.loadAdData){
                printLog("load $type success")
                isLoadingList.remove(type)
                adResMap[type]=it
            }else{
                printLog("load $type fail,${it.failMsg}")
                if (iterator.hasNext()){
                    startLoadAd(type,iterator,loadOpen)
                }else{
                    isLoadingList.remove(type)
                    if (type==AdType.AD_TYPE_OPEN&&loadOpen){
                        check(type,loadOpen = false)
                    }
                }
            }
        }
    }

    private fun checkIsLoading(type: String)=isLoadingList.contains(type)

    private fun hasAdRes(type: String):Boolean{
        if (adResMap.containsKey(type)){
            val loadedAdBean = adResMap[type]
            if (null!=loadedAdBean?.loadAdData){
                if (loadedAdBean.checkEx()){
                    clearAdResByType(type)
                }else{
                    return true
                }
            }
        }
        return false
    }


    private fun parseAdJson(type: String):List<ConfAdBean>{
        val list= arrayListOf<ConfAdBean>()
        try {
            val jsonArray = JSONObject(ReadFirebase.getAdJson()).getJSONArray(type)
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                list.add(
                    ConfAdBean(
                        jsonObject.optString("flower_id"),
                        jsonObject.optString("flower_type"),
                        jsonObject.optInt("flower_sort"),
                        jsonObject.optString("flower_source"),
                    )
                )
            }
        }catch (e:Exception){}
        return list.filter { it.source_flower == "admob" }.sortedByDescending { it.sort_flower }
    }

    fun getAdResData(type: String)= adResMap[type]?.loadAdData

    fun clearAdResByType(type: String){
        adResMap.remove(type)
    }
}