package com.flowerbrowse.unlimited.search.websites.photos.ui.browse

import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.RecentAdapter
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.bean.BookmarkBean
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusBean
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusCode
import com.flowerbrowse.unlimited.search.websites.photos.util.LitePalUtil
import kotlinx.android.synthetic.main.activity_recent.*

class RecentPage:BasePage(R.layout.activity_recent) {
    override fun initView() {
        immersionBar.statusBarView(top_view).init()
        val recentList = LitePalUtil.getRecentList()
        iv_back.setOnClickListener { finish() }
        rv_recent.apply {
            layoutManager=LinearLayoutManager(this@RecentPage)
            adapter=RecentAdapter(this@RecentPage,recentList){
                val bookmarkBean = BookmarkBean(
                    title = it.title,
                    webUrl = it.webUrl,
                    time = it.time,
                    iconUrl = it.iconUrl
                )
                EventbusBean(EventbusCode.CLICK_RECORD_ITEM, any = bookmarkBean).send()
                finish()
            }
        }
    }
}