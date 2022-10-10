package com.demo.browseseaching.ui

import android.content.Intent
import com.demo.browseseaching.R
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.ui.browse.BrowseHomePage
import com.demo.browseseaching.ui.server.ServerPage
import kotlinx.android.synthetic.main.activity_home.*

class HomePage: BasePage(R.layout.activity_home) {
    override fun initView() {
        immersionBar.statusBarView(top_view).init()

        llc_browse.setOnClickListener {
            startActivity(Intent(this, BrowseHomePage::class.java))
        }

        iv_set.setOnClickListener {
            startActivity(Intent(this, SettingPage::class.java))
        }

        llc_server.setOnClickListener {
            startActivity(Intent(this, ServerPage::class.java))
        }
    }
}