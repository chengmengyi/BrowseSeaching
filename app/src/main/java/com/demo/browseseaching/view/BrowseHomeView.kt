package com.demo.browseseaching.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.BrowseHomeFuncAdapter
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.bean.BrowseHomeFuncBean
import com.demo.browseseaching.dialog.SearchDialog
import com.demo.browseseaching.interfaces.IBrowseHomeClickListener
import com.demo.browseseaching.manager.PopupManager
import com.demo.browseseaching.ui.browse.BookmarkReadLaterPage
import com.demo.browseseaching.ui.browse.HistoryPage
import com.demo.browseseaching.util.copyText
import com.demo.browseseaching.util.shareUrl

class BrowseHomeView @JvmOverloads constructor(
    private val ctx: Context,
    private val homeClickListener: IBrowseHomeClickListener,
    attrs: AttributeSet? = null,
) : LinearLayout(ctx, attrs) {
    private var rvFunc:RecyclerView?=null
    private var rootLayout:ConstraintLayout?=null

    private val funcAdapter by lazy {
        BrowseHomeFuncAdapter(
            context,
            clickFunc = {
                clickFuncItem(it)
            },
            longClickFunc = { v, bean -> longClickFuncItem(v,bean)}
        )
    }

    private fun longClickFuncItem(view:View, funcBean: BrowseHomeFuncBean){
        when(funcBean.index){
            0,1,2,3->{
                PopupManager.showLongClickPopup(ctx,view){
                    if (it==0){
                        homeClickListener.openNewLabel(funcBean.webUrl?:"")
                    }
                    if (it==1){
                        homeClickListener.openWebUrlByIncognito(funcBean.webUrl?:"")
                    }
                    if (it==2){
                        ctx.copyText(funcBean.webUrl?:"")
                    }
                    if (it==3){
                        ctx.shareUrl(funcBean.webUrl?:"")
                    }
                }
            }
        }
    }

    private fun clickFuncItem(funcBean: BrowseHomeFuncBean){
        when(funcBean.index){
            7->context.startActivity(Intent(context,HistoryPage::class.java))
            4->context.startActivity(
                Intent(context,BookmarkReadLaterPage::class.java).apply {
                    putExtra("isBook",true)
                }
            )
            5->context.startActivity(
                Intent(context,BookmarkReadLaterPage::class.java).apply {
                    putExtra("isBook",false)
                }
            )
        }
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.browse_home_view, this)
        rvFunc=view.findViewById(R.id.rv_browse_home_func)
        rootLayout = view.findViewById(R.id.root_layout)

        view.findViewById<LinearLayoutCompat>(R.id.llc_search).setOnClickListener {
            if (ctx is FragmentActivity){
                val searchDialog = SearchDialog()
                searchDialog.show(ctx.supportFragmentManager,"SearchDialog")
            }
        }

        view.findViewById<AppCompatImageView>(R.id.iv_back).setOnClickListener {
            homeClickListener.back()
        }

        setAdapter()
    }

    fun getRootLayoutBitmap():Bitmap?{
        rootLayout?.isDrawingCacheEnabled=true
        rootLayout?.buildDrawingCache()
        val drawingCache = rootLayout?.drawingCache
        return drawingCache
    }

    private fun setAdapter(){
        rvFunc?.apply {
            layoutManager=GridLayoutManager(context,4)
            adapter=funcAdapter
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