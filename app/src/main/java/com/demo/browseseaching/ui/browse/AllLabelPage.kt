package com.demo.browseseaching.ui.browse

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.BrowseLabelAdapter
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.manager.BrowseLabelManager
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.view.BrowseContentView
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
                BrowseLabelManager.homeBitmap=null
                if (it){
                    finish()
                }
            },
            deleteFinish = {
                labelList.removeAt(index)
                labelAdapter.notifyDataSetChanged()
            }
        )
    }
}