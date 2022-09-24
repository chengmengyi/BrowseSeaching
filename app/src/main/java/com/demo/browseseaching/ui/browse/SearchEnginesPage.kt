package com.demo.browseseaching.ui.browse

import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.SearchEnginesAdapter
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.manager.SearchEngineManager
import kotlinx.android.synthetic.main.activity_search_engines.*

class SearchEnginesPage:BasePage(R.layout.activity_search_engines) {

    override fun initView() {
        immersionBar.statusBarView(top_view).init()
        iv_back.setOnClickListener { finish() }

        rv_search_engines.apply {
            layoutManager=LinearLayoutManager(this@SearchEnginesPage)
            adapter=SearchEnginesAdapter(this@SearchEnginesPage){
                click(it)
            }
        }
    }

    private fun click(index:Int){
        SearchEngineManager.setIndex(index)
        finish()
    }
}