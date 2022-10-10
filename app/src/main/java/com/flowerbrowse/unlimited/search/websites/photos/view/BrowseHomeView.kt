package com.flowerbrowse.unlimited.search.websites.photos.view

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
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.BrowseHomeFuncAdapter
import com.flowerbrowse.unlimited.search.websites.photos.bean.BookmarkBean
import com.flowerbrowse.unlimited.search.websites.photos.bean.BrowseHomeFuncBean
import com.flowerbrowse.unlimited.search.websites.photos.dialog.SearchDialog
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IBrowseHomeClickListener
import com.flowerbrowse.unlimited.search.websites.photos.manager.PopupManager
import com.flowerbrowse.unlimited.search.websites.photos.ui.browse.BookmarkReadLaterPage
import com.flowerbrowse.unlimited.search.websites.photos.ui.browse.HistoryPage
import com.flowerbrowse.unlimited.search.websites.photos.util.copyText
import com.flowerbrowse.unlimited.search.websites.photos.util.printLog
import com.flowerbrowse.unlimited.search.websites.photos.util.shareUrl

class BrowseHomeView @JvmOverloads constructor(
    private val ctx: Context,
    private var homeClickListener: IBrowseHomeClickListener,
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
            0,1,2,3->{
                homeClickListener.loadUrlCurrentLabel(funcBean.webUrl?:"")
            }
            4->homeClickListener.openLabelOrReadListPage(true)
            5->homeClickListener.openLabelOrReadListPage(false)
            6->homeClickListener.openRecent()
            7->homeClickListener.openHistoryPage()
        }
    }

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.browse_home_view, this,false)
        rvFunc=view.findViewById(R.id.rv_browse_home_func)
        rootLayout = view.findViewById(R.id.root_layout)

        view.findViewById<LinearLayoutCompat>(R.id.llc_search).setOnClickListener {
            homeClickListener.openSearch()
        }

        view.findViewById<AppCompatImageView>(R.id.iv_back).setOnClickListener {
            homeClickListener.back()
        }

        setAdapter()
        addView(view)
    }

    fun setListener(homeClickListener: IBrowseHomeClickListener){
        this.homeClickListener=homeClickListener
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
}