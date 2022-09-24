package com.demo.browseseaching.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.MoreFuncAdapter


class MorePopupWindow(
    private val context:Context,
    private val clickItem:(index:Int)->Unit
) {
    private var mPopupWindow:PopupWindow?=null
    private var moreFuncAdapter:MoreFuncAdapter?=null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_more_dialog, null)
        mPopupWindow=PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopupWindow?.isFocusable=true
        mPopupWindow?.isOutsideTouchable=true

        val rvMore = view.findViewById<RecyclerView>(R.id.rv_more)
        moreFuncAdapter= MoreFuncAdapter(context){
            clickItem.invoke(it)
            mPopupWindow?.dismiss()
        }
        rvMore.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=moreFuncAdapter
        }
    }

    fun show(view:View){
        mPopupWindow?.showAtLocation(view,Gravity.BOTTOM,0,SizeUtils.dp2px(66F))
    }

}