package com.demo.browseseaching.ui

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.SetAdapter
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.ui.browse.SearchEnginesPage
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
            4->startActivity(Intent(this,SearchEnginesPage::class.java))
        }
    }
}