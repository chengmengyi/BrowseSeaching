package com.demo.browseseaching.admob

import com.demo.browseseaching.bean.ConfAdBean
import com.demo.browseseaching.bean.LoadedAdBean
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.mMyApp
import com.demo.browseseaching.util.printLog
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions

abstract class LoadAdmob {

    protected fun loadAd(type:String,confAdBean: ConfAdBean,result:(bean:LoadedAdBean)->Unit){
        printLog("start load $type ad ,${confAdBean.toString()}")
        when(confAdBean.type_flower){
            "kai"->loadKai(confAdBean.id_flower,result)
            "cha"->loadCha(confAdBean.id_flower,result)
            "yuan"->loadYuan(confAdBean.id_flower,result)
        }
    }

    private fun loadKai(id:String,result:(bean:LoadedAdBean)->Unit){
        AppOpenAd.load(
            mMyApp,
            id,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback(){
                override fun onAdLoaded(p0: AppOpenAd) {
                    super.onAdLoaded(p0)
                    result.invoke(LoadedAdBean(loadTime = System.currentTimeMillis(), loadAdData = p0))
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    result.invoke(LoadedAdBean(failMsg = p0.message))
                }
            }
        )
    }

    private fun loadCha(id:String,result:(bean:LoadedAdBean)->Unit){
        InterstitialAd.load(
            mMyApp,
            id,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback(){

                override fun onAdLoaded(p0: InterstitialAd) {
                    result.invoke(LoadedAdBean(loadTime = System.currentTimeMillis(), loadAdData = p0))
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    result.invoke(LoadedAdBean(failMsg = p0.message))
                }
            }
        )
    }

    private fun loadYuan(id:String,result:(bean:LoadedAdBean)->Unit){
        AdLoader.Builder(
            mMyApp,
            id
        ).forNativeAd {
            result.invoke(LoadedAdBean(loadTime = System.currentTimeMillis(), loadAdData = it))
        }
            .withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    result.invoke(LoadedAdBean(failMsg = p0.message))
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    ReadFirebase.addClickNum()
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(
                        NativeAdOptions.ADCHOICES_TOP_LEFT
                    )
                    .build()
            )
            .build()
            .loadAd(AdRequest.Builder().build())
    }
}