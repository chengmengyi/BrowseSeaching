package com.flowerbrowse.unlimited.search.websites.photos.ui

import android.content.Intent
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.ui.browse.BrowseHomePage
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
    }
}