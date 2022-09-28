package com.demo.browseseaching.ui.browse

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.HistoryAdapter
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.ShowNativeAd
import com.demo.browseseaching.bean.HistoryBean
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.util.LitePalUtil
import com.demo.browseseaching.util.printLog
import com.demo.browseseaching.util.transTimeToStr
import com.demo.browseseaching.view.sticky.StickyItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.et_search
import kotlinx.android.synthetic.main.activity_history.top_view
import kotlinx.android.synthetic.main.activity_search.*

class HistoryPage: BasePage(R.layout.activity_history), OnRefreshLoadMoreListener {
    private val showAd by lazy { ShowNativeAd(this, AdType.AD_TYPE_HISTORY) }

    private var currentNum=0
    private var content=""
    private val historyList= arrayListOf<HistoryBean>()
    private val historyAdapter by lazy { HistoryAdapter(this@HistoryPage,historyList){
        val bookmarkBean = BookmarkBean(
            title = it.title,
            webUrl = it.webUrl,
            time = it.time,
            iconUrl = it.iconUrl
        )
        EventbusBean(EventbusCode.CLICK_RECORD_ITEM, any = bookmarkBean).send()
        finish()
    } }

    override fun initView() {
        immersionBar.statusBarView(top_view).init()
        iv_back.setOnClickListener { finish() }
        setAdapter()
        refresh_layout.setOnRefreshLoadMoreListener(this)
        refresh_layout.autoRefresh()


        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(textView: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
                if ((actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH) && keyEvent != null) {
                    currentNum=0
                    content = et_search.text.toString().trim()
                    hideSoftKeyBoard()
                    refresh_layout.autoRefresh()
                    return true
                }
                return false
            }
        })
    }

    private fun setAdapter() {
        for (index in 0 until historyList.size){
            val historyBean = historyList[index]
            historyBean.time= index.toLong()
            historyBean.save()
        }


        rv_history.apply {
            layoutManager=LinearLayoutManager(this@HistoryPage)
            addItemDecoration(StickyItemDecoration())
            adapter=historyAdapter
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        selectHistory()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        selectHistory()
    }

    private fun selectHistory() {
        val findAll = if (content.isNullOrEmpty()){
            LitePalUtil.queryHistory(currentNum)
        }else{
            LitePalUtil.searchHistory(currentNum,content)
        }

        if (!findAll.isNullOrEmpty()){
            if (currentNum==0){
                historyList.clear()
            }
            currentNum+=findAll.size
            if (historyList.isEmpty()){
                val first = findAll.first()
                historyList.add(HistoryBean(title = transTimeToStr(first.time), itemType = 11, time = first.time))
            }
            findAll.forEach {
                val last = historyList.last()
                val lastTime=transTimeToStr(last.time)
                val thisTime=transTimeToStr(it.time)
                if (lastTime.equals(thisTime)){
                    historyList.add(it)
                }else{
                    historyList.add(HistoryBean(title = thisTime, itemType = 11, time = it.time))
                    historyList.add(it)
                }
            }
            historyAdapter.notifyDataSetChanged()
        }
        if (refresh_layout.isRefreshing){
            refresh_layout.finishRefresh()
        }
        if (refresh_layout.isLoading){
            refresh_layout.finishLoadMore()
        }
    }

    private fun hideSoftKeyBoard() {
        if (et_search != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
        }
    }

    override fun onResume() {
        super.onResume()
        showAd.checkHasAdRes()
    }

    override fun onDestroy() {
        super.onDestroy()
        showAd.stopCheck()
    }
}