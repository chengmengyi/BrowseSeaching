package com.flowerbrowse.unlimited.search.websites.photos.ui.browse

import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.SearchEnginesAdapter
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.manager.SearchEngineManager
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