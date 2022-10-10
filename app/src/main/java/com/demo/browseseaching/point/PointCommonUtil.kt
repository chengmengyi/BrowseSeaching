package com.demo.browseseaching.point

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.google.android.a.c
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*
import kotlin.Exception
import java.security.NoSuchAlgorithmException




class PointCommonUtil {
    companion object{

        fun assembleCommonJson(context: Context,ip:String):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("strand", assembleStrandJson(context))
            jsonObject.put("septic", assembleSepticJson(context,ip))
            jsonObject.put("elution", assembleElutionJson(context))
            jsonObject.put("tacky", assembleTackyJson(context))
            return jsonObject
        }


        private fun assembleStrandJson(context: Context):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("drip", getGaid(context))
            jsonObject.put("kalmuk", getLogId())
            jsonObject.put("drub", getBrand())
            jsonObject.put("freight", getLanguage())
            jsonObject.put("punky", 0)
            jsonObject.put("linen", context.packageName)
            jsonObject.put("mortem", getOsCountry())
            return jsonObject
        }

        private fun assembleSepticJson(context: Context,ip:String):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("deferred", getDeviceModel())
            jsonObject.put("minor", getAndroidID(context))
            jsonObject.put("tree", getManufacturer())
            jsonObject.put("beast", ip)
            jsonObject.put("sit", getAppVersion(context))
            return jsonObject
        }

        private fun assembleElutionJson(context: Context):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("megabyte", encrypt(getAndroidID(context)))
            jsonObject.put("godson", "behold")
            jsonObject.put("erratic", getZoneOffset())
            return jsonObject
        }

        private fun assembleTackyJson(context: Context):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("blab", getOperator(context))
            jsonObject.put("chowder",System.currentTimeMillis())
            jsonObject.put("opacity", getNetType(context))
            jsonObject.put("bathurst", getOsVersion())
            return jsonObject
        }

        private fun getGaid(context: Context)=try {
            AdvertisingIdClient.getAdvertisingIdInfo(context).id
        }catch (e:Exception){
            ""
        }

        private fun getLogId()= UUID.randomUUID().toString()

        private fun getBrand()= android.os.Build.BRAND

        private fun getLanguage():String{
            val default = Locale.getDefault()
            return "${default.language}_${default.country}"
        }

        private fun getOsCountry()=Locale.getDefault().country

        private fun getAndroidID(context: Context): String {
            try {
                val id: String = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
                )
                return if ("9774d56d682e549c" == id) "" else id ?: ""
            }catch (e:Exception){

            }
            return ""
        }

        private fun getZoneOffset()=TimeZone.getDefault().rawOffset/3600/1000

        private fun getOperator(context: Context):String{
            try {
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                return telephonyManager.networkOperator
            }catch (e:Exception){

            }
            return ""
        }

        private fun getNetType(context: Context):String{
            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (null==connectivityManager){
                    return "no"
                }
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (null==activeNetworkInfo||activeNetworkInfo.isAvailable){
                    return "no"
                }
                val wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (null!=wifiInfo){
                    val state = wifiInfo.state
                    if (null!=state){
                        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                            return "wifi"
                        }
                    }
                }
                return "mobile"
            }catch (ex:Exception){

            }
            return "no"
        }

        private fun getOsVersion()=Build.VERSION.RELEASE

        private fun getDeviceModel()=Build.MODEL

        private fun getManufacturer()=Build.MANUFACTURER

        private fun getAppVersion(context: Context)=context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_META_DATA).versionName

        private fun encrypt(raw: String): String {
            var md5Str = raw
            try {
                val md = MessageDigest.getInstance("MD5")
                md.update(raw.toByteArray())
                val encryContext = md.digest()
                var i: Int
                val buf = StringBuffer("")
                for (offset in encryContext.indices) {
                    i = encryContext[offset].toInt()
                    if (i < 0) {
                        i += 256
                    }
                    if (i < 16) {
                        buf.append("0")
                    }
                    buf.append(Integer.toHexString(i))
                }
                md5Str = buf.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return md5Str
        }
    }
}