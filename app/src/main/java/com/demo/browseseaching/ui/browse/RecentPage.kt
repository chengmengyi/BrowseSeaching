package com.demo.browseseaching.ui.browse

import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.RecentAdapter
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.ShowNativeAd
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.util.LitePalUtil
import kotlinx.android.synthetic.main.activity_recent.*

class RecentPage:BasePage(R.layout.activity_recent) {
    private val showAd by lazy { ShowNativeAd(this,AdType.AD_TYPE_RECENT) }

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

    override fun onResume() {
        super.onResume()
        showAd.checkHasAdRes()
    }

    override fun onDestroy() {
        super.onDestroy()
        showAd.stopCheck()
    }
}