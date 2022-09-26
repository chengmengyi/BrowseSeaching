package com.demo.browseseaching.view

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.demo.browseseaching.R
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.interfaces.IUpdateBottomBtnListener
import com.demo.browseseaching.util.LitePalUtil
import com.demo.browseseaching.util.show
import java.lang.Exception

class BrowseWebView @JvmOverloads constructor(
    private val ctx: Context,
    private val incognito:Boolean,
    private var listener:IUpdateBottomBtnListener,
    attrs: AttributeSet? = null,
) : LinearLayout(ctx, attrs){

    private var tvTitle:AppCompatTextView?=null
    private var webProgress: ProgressBar?=null
    private lateinit var webView:WebView


    private var webUrl:String=""

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.browse_web_view, this)
        webView=view.findViewById(R.id.web_view)
        tvTitle=view.findViewById(R.id.tv_url)
        webProgress=view.findViewById(R.id.web_progress)
        view.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            reLoadUrl()
        }

        view.findViewById<AppCompatImageView>(R.id.iv_web_home).setOnClickListener {
            EventbusBean(EventbusCode.LOAD_HOME).send()
        }

        setWebView()
    }

    fun loadUrl(url:String){
        this.webUrl=url
        webView.loadUrl(url)
    }

    fun reLoadUrl(){
        webProgress?.show(true)
        webView.reload()
    }

    fun addReadLater(){
        LitePalUtil.saveReadLater(ctx,tvTitle?.text?.toString()?:"",webView.url?:"")
    }

    fun addBookmark(){
        LitePalUtil.saveBookmark(ctx,tvTitle?.text?.toString()?:"",webView.url?:"")
    }

    fun getWebViewBitmap():Bitmap?{
        return try {
            webView.isDrawingCacheEnabled=true
            webView.drawingCache
        }catch (e:Exception){
            null
        }
    }

    fun setListener(listener:IUpdateBottomBtnListener){
        this.listener=listener
    }

    private fun setWebView(){
        webView.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_YES
            }
            isScrollbarFadingEnabled = true
            isSaveEnabled = true
            overScrollMode = View.OVER_SCROLL_NEVER
            setNetworkAvailable(true)
            with(webView.settings) {
                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = true

                mixedContentMode = if (!incognito) {
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                } else {
                    WebSettings.MIXED_CONTENT_NEVER_ALLOW
                }

                if (!incognito) {
                    domStorageEnabled = true
                    setAppCacheEnabled(true)
                    databaseEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                } else {
                    domStorageEnabled = false
                    setAppCacheEnabled(false)
                    databaseEnabled = false
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }

                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                allowContentAccess = true
                allowFileAccess = true
                allowFileAccessFromFileURLs = false
                allowUniversalAccessFromFileURLs = false
            }
            webViewClient=object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                    webView.loadUrl(url?:"")

                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    LitePalUtil.saveHistoryUrl(view?.title?:"",url?:"")
                    listener.updateBottomBtnState(updateBack = true, updateForward = webView.canGoForward(),updateMore = true)
                }
            }
            webChromeClient=object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    webProgress?.progress=newProgress
                    if (newProgress>=100){
                        webProgress?.show(false)
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    tvTitle?.text=title?:""
                }

                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    super.onReceivedIcon(view, icon)

                }
            }
        }
    }

    fun back():Boolean{
        return if (webView.canGoBack()){
            webView.goBack()
            true
        }else{
            false
        }
    }


    fun forward():Boolean{
        return if (webView.canGoForward()){
            webView.goForward()
            true
        }else{
            false
        }
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val count = childCount
//        for (i in 0 until count) {
//            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec)
//        }
//    }
}