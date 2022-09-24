package com.demo.browseseaching.view

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import com.demo.browseseaching.interfaces.IBrowseHomeClickListener
import com.demo.browseseaching.interfaces.IUpdateBottomBtnListener
import com.demo.browseseaching.manager.BrowseLabelManager

class BrowseContentView(
    private val context:Context,
    private val listener: IUpdateBottomBtnListener,
    private val homeClickListener: IBrowseHomeClickListener,
    private val incognito:Boolean=false,
) {
    private var showHome=true
    private var viewList= arrayListOf<View>()
    var createTime=0L

    init {
        viewList.clear()
        createTime=System.currentTimeMillis()
        viewList.add(BrowseHomeView(context,homeClickListener))
    }

    fun getHomeBitmap():Bitmap?{
        if (viewList.size>=0){
            val first = viewList.first()
            if (first is BrowseHomeView){
                return first.getRootLayoutBitmap()
            }
        }
        return null
    }

    fun getShowView():View{
        if (viewList.size==0){
            viewList.add(BrowseHomeView(context,homeClickListener))
        }
        if (!showHome&&viewList.size==2){
            return viewList.last()
        }
        return viewList.first()
    }

    fun loadUrl(url:String,success:()->Unit){
        if (viewList.size==1){
            viewList.add(BrowseWebView(context,incognito, listener = listener))
        }
        val last = viewList.last()
        if (last is BrowseWebView){
            changeView(false)
            last.loadUrl(url)
            success.invoke()
        }
    }

    fun reloadUrl(){
        if (viewList.size==2){
            val last = viewList.last()
            if (last is BrowseWebView){
                last.reLoadUrl()
            }
        }
    }

    fun addReadLater(){
        if (viewList.size==2){
            val last = viewList.last()
            if (last is BrowseWebView){
                last.addReadLater()
            }
        }
    }

    fun addBookmark(){
        if (viewList.size==2){
            val last = viewList.last()
            if (last is BrowseWebView){
                last.addBookmark()
            }
        }
    }

    fun hasWebView()=viewList.size==2

    fun showingHome()=showHome

    private fun changeView(showHome:Boolean){
        this.showHome=showHome
        listener.updateBottomBtnState(updateBack = true, updateForward = true,updateMore = true)
    }

    fun clickBottomBack(){
        val showView = getShowView()
        if (showView is BrowseWebView){
            if (!showView.back()){
                changeView(true)
                BrowseLabelManager.updateShowContentView()
            }
        }
    }

    fun clickBottomFroward(){
        if (showHome){
            if (viewList.size==2){
                changeView(false)
                BrowseLabelManager.updateShowContentView()
            }
        }else{
            val showView = getShowView()
            if (showView is BrowseWebView){
                showView.forward()
            }
        }
    }

    fun showHome(){
        if (!showingHome()&&viewList.size==2){
            changeView(true)
        }
    }

    fun onDestroy(){
        viewList.forEach {
            val parent = it.parent
            if (null!=parent&& parent is ViewGroup){
                parent.removeView(it)
            }
        }
    }
}