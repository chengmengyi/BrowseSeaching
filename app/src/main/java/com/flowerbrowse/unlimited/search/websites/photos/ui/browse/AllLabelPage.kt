package com.flowerbrowse.unlimited.search.websites.photos.ui.browse

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.BrowseLabelAdapter
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusBean
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusCode
import com.flowerbrowse.unlimited.search.websites.photos.manager.BrowseLabelManager
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.view.BrowseContentView
import kotlinx.android.synthetic.main.activity_all_label.*

class AllLabelPage: BasePage(R.layout.activity_all_label) {
    private val labelList= arrayListOf<BrowseContentView>()
    private lateinit var labelAdapter:BrowseLabelAdapter

    override fun initView() {
        immersionBar.statusBarView(top_view).init()

        labelList.addAll(BrowseLabelManager.getLabelList())
        labelAdapter=BrowseLabelAdapter(
            this,
            labelList,
            click = {
                BrowseLabelManager.updateShowIndex(it)
                finish()
            },
            deleteLabel = {
                deleteLabel(it)
            }
        )
        rv_label.apply {
            val gridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            gridLayoutManager.reverseLayout=true
            layoutManager=gridLayoutManager
            adapter=labelAdapter
        }

        iv_add_label.setOnClickListener {
            EventbusBean(EventbusCode.ADD_LABEL, boolean = false).send()
            finish()
        }
        tv_private.setOnClickListener {
            EventbusBean(EventbusCode.ADD_LABEL, boolean = true).send()
            finish()
        }
        tv_cancel.setOnClickListener { finish() }
    }

    private fun deleteLabel(index: Int){
        val clickLabelView=labelList[index]
        BrowseLabelManager.removeContentView(
            index,
            clickLabelView,
            result = {
                if (it){
                    finish()
                }
            },
            deleteFinish = {
                labelList.removeAt(index)
                BrowseLabelManager.getHomeBitmap()
                labelAdapter.notifyDataSetChanged()
            }
        )
    }
}