package com.flowerbrowse.unlimited.search.websites.photos.manager

import android.content.Context
import android.graphics.Bitmap
import com.flowerbrowse.unlimited.search.websites.photos.bean.BookmarkBean
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IBrowseHomeClickListener
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IUpdateBottomBtnListener
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IUpdateContentViewListener
import com.flowerbrowse.unlimited.search.websites.photos.util.printLog
import com.flowerbrowse.unlimited.search.websites.photos.view.BrowseContentView

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