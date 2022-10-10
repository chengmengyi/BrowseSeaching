package com.demo.browseseaching.admob

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.SizeUtils
import com.demo.browseseaching.R
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.util.printLog
import com.demo.browseseaching.util.show
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.coroutines.*

class ShowNativeAd (private val basePage: BasePage, private val type:String){
    private var launch:Job?=null
    private var preAdResData:NativeAd?=null

    fun checkHasAdRes(){
        LoadAdManager.check(type)
        stopCheck()
        startCheck()
    }

    private fun startCheck(){
        launch = GlobalScope.launch(Dispatchers.Main) {
            delay(300L)
            if (basePage.resume) {
                while (true) {
                    if (!isActive) {
                        break
                    }
                    val adResData = LoadAdManager.getAdResData(type)
                    if (null!=adResData&&basePage.resume&&adResData is NativeAd){
                        cancel()
                        preAdResData?.destroy()
                        preAdResData = adResData
                        startShowNativeAd(adResData)
                    }
                    delay(1000L)
                }
            }
        }
    }


    private fun startShowNativeAd(nativeAd: NativeAd){
        when(type){
            AdType.AD_TYPE_RECENT,AdType.AD_TYPE_HISTORY->showBookmarkNativeAd(nativeAd)
            AdType.AD_TYPE_CONNECT_RESULT,AdType.AD_TYPE_CONNECT_HOME->showNativeAd(nativeAd)
        }
    }

    private fun showNativeAd(nativeAd: NativeAd){
        printLog("start show $type ad")
        val viewNativeAd = basePage.findViewById<NativeAdView>(R.id.view_native_ad)
        viewNativeAd.mediaView=basePage.findViewById(R.id.iv_ad_cover)
        if (null!=nativeAd.mediaContent){
            viewNativeAd.mediaView?.apply {
                setMediaContent(nativeAd.mediaContent)
                setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        if (view == null || outline == null) return
                        outline.setRoundRect(
                            0,
                            0,
                            view.width,
                            view.height,
                            SizeUtils.dp2px(10F).toFloat()
                        )
                        view.clipToOutline = true
                    }
                }
            }
        }
        viewNativeAd.headlineView=basePage.findViewById(R.id.tv_ad_title)
        (viewNativeAd.headlineView as AppCompatTextView).text=nativeAd.headline

        viewNativeAd.bodyView=basePage.findViewById(R.id.tv_ad_desc)
        (viewNativeAd.bodyView as AppCompatTextView).text=nativeAd.body

        viewNativeAd.iconView=basePage.findViewById(R.id.iv_ad_logo)
        (viewNativeAd.iconView as ImageFilterView).setImageDrawable(nativeAd.icon?.drawable)

        viewNativeAd.callToActionView=basePage.findViewById(R.id.tv_ad_install)
        (viewNativeAd.callToActionView as AppCompatTextView).text=nativeAd.callToAction

        viewNativeAd.setNativeAd(nativeAd)
        ReadFirebase.addAdShowNum()
        basePage.findViewById<AppCompatImageView>(R.id.iv_default).show(false)

        LoadAdManager.clearAdResByType(type)
        LoadAdManager.getAdResData(type)
    }


    private fun showBookmarkNativeAd(nativeAd: NativeAd){
        printLog("start show $type ad")
        val viewNativeAd = basePage.findViewById<NativeAdView>(R.id.view_native_ad)
        viewNativeAd.headlineView=basePage.findViewById(R.id.tv_ad_title)
        (viewNativeAd.headlineView as AppCompatTextView).text=nativeAd.headline

        viewNativeAd.bodyView=basePage.findViewById(R.id.tv_ad_desc)
        (viewNativeAd.bodyView as AppCompatTextView).text=nativeAd.body

        viewNativeAd.iconView=basePage.findViewById(R.id.iv_ad_logo)
        (viewNativeAd.iconView as ImageFilterView).setImageDrawable(nativeAd.icon?.drawable)

        viewNativeAd.callToActionView=basePage.findViewById(R.id.tv_ad_install)
        (viewNativeAd.callToActionView as AppCompatTextView).text=nativeAd.callToAction

        viewNativeAd.setNativeAd(nativeAd)
        ReadFirebase.addAdShowNum()
        basePage.findViewById<AppCompatImageView>(R.id.iv_default).show(false)
        viewNativeAd.show(true)

        LoadAdManager.clearAdResByType(type)
        LoadAdManager.getAdResData(type)
    }

    fun stopCheck(){
        launch?.cancel()
        launch=null
    }
}