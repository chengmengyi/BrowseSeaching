package com.demo.browseseaching.manager

import android.content.Context
import android.graphics.Bitmap
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.interfaces.IBrowseHomeClickListener
import com.demo.browseseaching.interfaces.IUpdateBottomBtnListener
import com.demo.browseseaching.interfaces.IUpdateContentViewListener
import com.demo.browseseaching.util.printLog
import com.demo.browseseaching.view.BrowseContentView

object BrowseLabelManager {
    private var currentIndex=0
    private var iUpdateContentViewListener: IUpdateContentViewListener?=null
    private val labelList= arrayListOf<BrowseContentView>()
    var homeBitmap:Bitmap?=null

    fun initView(
        context:Context,
        listener:IUpdateBottomBtnListener,
        iUpdateContentViewListener: IUpdateContentViewListener,
        iBrowseHomeClickListener: IBrowseHomeClickListener
    ) {
        this.iUpdateContentViewListener=iUpdateContentViewListener
        if (labelList.isEmpty()){
            labelList.add(BrowseContentView(context,listener,iBrowseHomeClickListener))
        }else{
            labelList.forEach {
                it.setAllListener(iBrowseHomeClickListener,listener)
            }
        }
    }

    fun getHomeBitmap(){
        if (labelList.size>=0){
//            homeBitmap= labelList.first().getHomeBitmap()
            for (browseContentView in labelList) {
                if(browseContentView.showingHome()){
                    homeBitmap=browseContentView.getHomeBitmap()
                    return
                }
            }
        }
    }

    fun updateShowIndex(index:Int){
        this.currentIndex=index
        updateShowContentView()
    }

    fun setShowIndex(index: Int){
        this.currentIndex=index
    }

    fun updateShowContentView(){
        iUpdateContentViewListener?.updateShowContentView()
    }

    fun getLabelList()=labelList

    fun getCurrentLabelView()=labelList[currentIndex]

    fun removeContentView(index: Int, clickLabelView: BrowseContentView,result: (finish:Boolean) -> Unit,deleteFinish:()->Unit){
        if (index < labelList.size){
            val currentLabelView = getCurrentLabelView()
            labelList.removeAt(index)
            if (clickLabelView.createTime==currentLabelView.createTime&& labelList.isNotEmpty()){
                updateShowIndex(0)
            }
            if (labelList.isEmpty()){
                iUpdateContentViewListener?.deleteContentView()
                result.invoke(true)
            }
            deleteFinish.invoke()
        }
    }

    fun addLabel(
        context: Context,
        listener:IUpdateBottomBtnListener,
        iBrowseHomeClickListener: IBrowseHomeClickListener,
        incognito:Boolean=false,
        result:()->Unit
    ){
        labelList.add(0,BrowseContentView(context,listener,iBrowseHomeClickListener,incognito= incognito))
        currentIndex=0
        result.invoke()
    }

    fun addWebLabel(
        context: Context,
        url:String,
        listener:IUpdateBottomBtnListener,
        iBrowseHomeClickListener: IBrowseHomeClickListener,
        incognito:Boolean=false,
        result:()->Unit
    ){
        val browseContentView = BrowseContentView(context, listener,iBrowseHomeClickListener,incognito=incognito)
        browseContentView.loadUrl(url){
            labelList.add(0,browseContentView)
            currentIndex=0
            listener.updateBottomBtnState(updateBack = true, updateMore = true)
            result.invoke()
        }
    }
}