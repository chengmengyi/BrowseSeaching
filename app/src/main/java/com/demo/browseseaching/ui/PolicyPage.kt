package com.demo.browseseaching.ui

import com.demo.browseseaching.R
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.config.Config
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