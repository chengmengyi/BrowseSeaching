package com.flowerbrowse.unlimited.search.websites.photos.ui

import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.config.Config
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyPage:BasePage(R.layout.activity_policy) {
    override fun initView() {
        immersionBar.statusBarView(top_view)?.init()
        iv_back.setOnClickListener { finish() }

        web_view.apply {
            settings.javaScriptEnabled=true
            loadUrl(Config.url)
        }
    }
}