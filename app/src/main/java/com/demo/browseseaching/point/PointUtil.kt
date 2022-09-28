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
        private const val INSTALL="install"

        fun uploadTBA(context: Context){
            OkgoUtil.requestGet("https://ip.seeip.org/geoip/"){
                GlobalScope.launch {
//                    val jsonObject = PointCommonUtil.assembleCommonJson(context, getIp(it))
//                    getInstallReferrerClient(context,jsonObject)
//                    assembleSessionJson(jsonObject)

                    OkgoUtil.uploadInstall(JSONObject(),false)
                }
            }

        }

        private fun getInstallReferrerClient(context: Context, jsonObject: JSONObject){
            if (getInstallTag()<=0){
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
                            }
                        } catch (e: Exception) {

                        }
                    }
                    override fun onInstallReferrerServiceDisconnected() {
                    }
                })
            }
        }

        private fun assembleInstallJson(context: Context,response: ReferrerDetails,jsonObject: JSONObject) {
            jsonObject.put("lome", getBuild())
            jsonObject.put("ovulate",response.installReferrer)
            jsonObject.put("rang",response.installVersion)
            jsonObject.put("rote", response.referrerClickTimestampSeconds)
            jsonObject.put("warhead", response.installBeginTimestampSeconds)
            jsonObject.put("thoracic", response.referrerClickTimestampServerSeconds)
            jsonObject.put("poetic", response.installBeginTimestampServerSeconds)
            jsonObject.put("bainite", response.googlePlayInstantParam)
            jsonObject.put("eurydice", getDefaultUserAgent(context))
            jsonObject.put("aberdeen", getFirstInstallTime(context))
            jsonObject.put("argue", getLastUpdateTime(context))
            val json=JSONObject()
            json.put("schwab",0)
            json.put("treaty",1)
            jsonObject.put("ar",json)
            jsonObject.put("dud","chelate")

            OkgoUtil.uploadInstall(jsonObject,true)
        }

        private fun assembleSessionJson(jsonObject: JSONObject) {
            jsonObject.put("honshu",JSONObject())
            printLog(jsonObject.toString())
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

        private fun getInstallTag()=MMKVUtil.read(INSTALL)

        fun saveInstallTag(){
            MMKVUtil.writeInt(INSTALL,1)
        }

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