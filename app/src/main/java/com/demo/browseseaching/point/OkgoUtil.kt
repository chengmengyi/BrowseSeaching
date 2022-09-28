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
                printLog("=onError===${response?.message()}")
            }
        })
    }


    const val s=""" {"strand": {"drip": "", "kalmuk": "4f5dFd48bd166eEc0AaCB1E4ed5545eBB", "plantain": "", "drub": "", "freight": "zh_CN", "punky": null, "linen": "com.example.my", "syndic": "", "uphill": "1.2.4", "mortem": null}, "septic": {"deferred": "p30", "minor": "auijiajfoa89", "sunday": "", "tree": "opple", "lapelled": "gp", "beast": "1.2.3.4", "sit": "1.3.1", "wispy": "myroom"}, "elution": {"megabyte": "26e763eb5d86A14CEdedCBA810a1f7Cc", "godson": "temper", "bode": "", "erratic": 0}, "tacky": {"blab": "43003", "chowder": 1664329264129, "opacity": "wifi", "bathurst": "10.1", "diffract": ""}, "lome": "ADaBafdF1CD", "ovulate": "utm_source=google-play&utm_medium=organic", "rang": "1.1.1", "eurydice": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36", "ar": "schwab", "rote": 0, "warhead": 0, "thoracic": 0, "poetic": 0, "aberdeen": 0, "argue": 0, "bainite": false, "dud": "chelate"}"""

    fun uploadInstall(jsonObject: JSONObject,install:Boolean){
        val path = "$url?kalmuk=${UUID.randomUUID()}&chowder=${System.currentTimeMillis()}"
        printLog(path)
        OkGo.post<String>(path)
            .retryCount(2)
            .headers("content-type","application/json")
            .headers("mortem", Locale.getDefault().country)
//            .upJson(jsonObject)
            .upJson(s)
            .execute(object :StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    if (install){
                        PointUtil.saveInstallTag()
                    }
                    Log.e("qwer","=onSuccess==${response?.code()}===${response?.message()}===${response?.body()}==")
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    Log.e("qwer","=onError==${response?.code()}===${response?.message()}=")
                }
            })
    }
}