package com.demo.browseseaching.point

import android.util.Log
import com.demo.browseseaching.util.printLog
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import org.json.JSONObject
import java.util.*

object OkgoUtil {
    const val url="http://pessimaltehran-93fd181fb2ebb945.elb.us-east-1.amazonaws.com/pessimal/tehran/kelly"

    fun requestGet(url:String,result:(json:String)-> Unit){
        OkGo.get<String>(url).execute(object : StringCallback(){
            override fun onSuccess(response: Response<String>?) {
                result(response?.body().toString())
            }

            override fun onError(response: Response<String>?) {
                super.onError(response)
//                printLog("=onError===${response?.message()}")
            }
        })
    }

    fun uploadInstall(jsonObject: JSONObject,install:Boolean){
        val path = "$url?kalmuk=${UUID.randomUUID()}&chowder=${System.currentTimeMillis()}"
        Log.e("qweraaa",jsonObject.toString())
        OkGo.post<String>(path)
            .retryCount(2)
            .headers("content-type","application/json")
            .headers("mortem", Locale.getDefault().country)
            .upJson(jsonObject)
            .execute(object :StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    if (install){
                        if (jsonObject.optString("ovulate").isEmpty()){
                            PointUtil.saveNoReferrerTag()
                        }else{
                            PointUtil.saveHasReferrerTag()
                        }
                    }
                    Log.e("qweraaa","=onSuccess==${response?.code()}===${response?.message()}===${response?.body()}==")
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
//                    Log.e("qwer","=onError==${response?.code()}===${response?.message()}==${response?.body()}==")
                }
            })
    }
}