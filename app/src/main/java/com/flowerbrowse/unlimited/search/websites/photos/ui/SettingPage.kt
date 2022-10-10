package com.flowerbrowse.unlimited.search.websites.photos.ui

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.SetAdapter
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.ui.browse.SearchEnginesPage
import com.flowerbrowse.unlimited.search.websites.photos.util.contact
import com.flowerbrowse.unlimited.search.websites.photos.util.shareApp
import com.flowerbrowse.unlimited.search.websites.photos.util.updateApp
import kotlinx.android.synthetic.main.activity_setting.*

class SettingPage:BasePage(R.layout.activity_setting) {
    override fun initView() {
        immersionBar.statusBarView(top_view).init()
        setAdapter()
        iv_back.setOnClickListener { finish() }
    }

    private fun setAdapter() {
        rv_set.apply {
            layoutManager=LinearLayoutManager(this@SettingPage)
            adapter=SetAdapter(this@SettingPage){
                click(it)
            }
        }
    }

    private fun click(index:Int){
        when(index){
            0->contact()
            1->startActivity(Intent(this,PolicyPage::class.java))
            2->shareApp()
            3->updateApp()
            4->startActivity(Intent(this,SearchEnginesPage::class.java))
        }
    }
}