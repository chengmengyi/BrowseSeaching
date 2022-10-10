package com.flowerbrowse.unlimited.search.websites.photos.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.LongClickAdapter

class LongClickHomeFuncPopup(
    private val ctx: Context,
) {
    private var click:((index:Int)->Unit)?=null
    private var mAdapter:LongClickAdapter?=null
    private var mPopupWindow: PopupWindow?=null

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.layout_long_click_popup, null)
        mPopupWindow=PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopupWindow?.isFocusable=true
        mPopupWindow?.isOutsideTouchable=true

        mAdapter=LongClickAdapter(ctx){
            mPopupWindow?.dismiss()
            click?.invoke(it)
        }
        val rvFunc=view.findViewById<RecyclerView>(R.id.rv_func)
        rvFunc.apply {
            layoutManager=LinearLayoutManager(ctx)
            adapter=mAdapter
        }
    }


    fun showPopup(view:View,click:(index:Int)->Unit){
        mPopupWindow?.showAsDropDown(view)
        this.click=click
    }
}