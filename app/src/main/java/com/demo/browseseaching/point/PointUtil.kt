package com.demo.browseseaching.point

import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.demo.browseseaching.mMyApp
import com.demo.browseseaching.mmkv.MMKVUtil
import com.demo.browseseaching.util.printLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class PointUtil {
    companion object{

        fun uploadTBA(context: Context){
            OkgoUtil.requestGet("https://ip.seeip.org/geoip/"){
                GlobalScope.launch {
                    val ip = getIp(it)
                    val jsonObject = PointCommonUtil.assembleCommonJson(context, ip)
                    getInstallReferrerClient(context,jsonObject)
                    assembleSessionJson(context,ip)
                }
            }

        }

        private fun getInstallReferrerClient(context: Context, jsonObject: JSONObject){
            if (!uploadHasReferrerTag() || !uploadNoReferrerTag()){
                val referrerClient = InstallReferrerClient.newBuilder(mMyApp).build()
                referrerClient.startConnection(object : InstallReferrerStateListener {
                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        try {
                            referrerClient.endConnection()
                            when (responseCode) {
                                InstallReferrerClient.InstallReferrerResponse.OK -> {
                                    val response = referrerClient.installReferrer
                                    assembleInstallJson(context,response,jsonObject)
                                }
                                else->{
                                    assembleInstallJson(context,null,jsonObject)
                                }
                            }
                        } catch (e: Exception) {

                        }
                    }
                    override fun onInstallReferrerServiceDisconnected() {
                    }
                })
            }
        }

        private fun assembleInstallJson(context: Context,response: ReferrerDetails?,jsonObject: JSONObject) {
            if (null==response&& uploadNoReferrerTag()){
                return
            }
            if (null!=response&& uploadHasReferrerTag()){
                return
            }
            jsonObject.put("lome", getBuild())
            if (null==response){
                jsonObject.put("ovulate","")
                jsonObject.put("rang","")
                jsonObject.put("rote", 0)
                jsonObject.put("warhead", 0)
                jsonObject.put("thoracic", 0)
                jsonObject.put("poetic", 0)
                jsonObject.put("bainite", false)
            }else{
                jsonObject.put("ovulate",response.installReferrer)
                jsonObject.put("rang",response.installVersion)
                jsonObject.put("rote", response.referrerClickTimestampSeconds)
                jsonObject.put("warhead", response.installBeginTimestampSeconds)
                jsonObject.put("thoracic", response.referrerClickTimestampServerSeconds)
                jsonObject.put("poetic", response.installBeginTimestampServerSeconds)
                jsonObject.put("bainite", response.googlePlayInstantParam)
            }

            jsonObject.put("eurydice", getDefaultUserAgent(context))
            jsonObject.put("aberdeen", getFirstInstallTime(context))
            jsonObject.put("argue", getLastUpdateTime(context))
            jsonObject.put("ar","schwab")
            jsonObject.put("dud","chelate")

            OkgoUtil.uploadInstall(jsonObject,true)
        }

        private fun assembleSessionJson(context: Context,ip:String) {
            val jsonObject = PointCommonUtil.assembleCommonJson(context, ip)
            jsonObject.put("honshu",JSONObject())
            OkgoUtil.uploadInstall(jsonObject,false)
        }

        private fun getBuild():String = "build/${Build.VERSION.RELEASE}"

        private fun getDefaultUserAgent(context: Context) = WebSettings.getDefaultUserAgent(context)

        private fun getFirstInstallTime(context: Context):Long{
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.firstInstallTime
            }catch (e:java.lang.Exception){

            }
            return System.currentTimeMillis()
        }

        private fun getLastUpdateTime(context: Context):Long{
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.lastUpdateTime
            }catch (e:java.lang.Exception){

            }
            return System.currentTimeMillis()
        }

        fun saveNoReferrerTag(){
            MMKVUtil.writeInt("noReferrer",1)
        }

        private fun uploadNoReferrerTag()=MMKVUtil.read("noReferrer")==1

        fun saveHasReferrerTag(){
            MMKVUtil.writeInt("hasReferrer",1)
        }

        private fun uploadHasReferrerTag()=MMKVUtil.read("hasReferrer")==1

        private fun getIp(json:String):String{
            try {
                if (json.isNullOrEmpty()){
                    return ""
                }
                val jsonObject=JSONObject(json)
                return jsonObject.optString("ip")
            }catch (e:Exception){}
            return ""
        }
    }
}