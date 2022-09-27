package com.demo.browseseaching.admob

import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.util.printLog
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShowFullAd(
    private val basePage: BasePage,
    private val type: String,
    private val showCompleted:()->Unit
) {

    fun checkShow(refreshUI:(jump:Boolean)->Unit){
        val adResData = LoadAdManager.getAdResData(type)
        if (null==adResData&&ReadFirebase.checkLoadAdIsLimit()){
            refreshUI.invoke(true)
            return
        }
        if (null!=adResData){
            refreshUI.invoke(false)
            if (LoadAdManager.isShowingFullAd||!basePage.resume){
                return
            }

            if (adResData is AppOpenAd){
                adResData.fullScreenContentCallback=showCallback
                adResData.show(basePage)
            }
            if (adResData is InterstitialAd){
                adResData.fullScreenContentCallback=showCallback
                adResData.show(basePage)
            }
        }
    }

    private val showCallback=object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            LoadAdManager.isShowingFullAd=false
            fullAdShowCompleted()
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
            ReadFirebase.addAdShowNum()
            LoadAdManager.isShowingFullAd=true
            LoadAdManager.clearAdResByType(type)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            LoadAdManager.isShowingFullAd=false
            LoadAdManager.clearAdResByType(type)
            fullAdShowCompleted()
        }

        override fun onAdClicked() {
            super.onAdClicked()
            ReadFirebase.addClickNum()
        }
    }

    private fun fullAdShowCompleted(){
//        if (adLocation==Ad0914LocationStr.AD_CONNECT){
//            Load0914AdObject.loadLogic(adLocation)
//        }
        GlobalScope.launch(Dispatchers.Main) {
            delay(200L)
            if (basePage.resume){
                showCompleted.invoke()
            }
        }
    }
}