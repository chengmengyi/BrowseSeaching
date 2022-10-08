package com.demo.browseseaching.ui.browse

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.BookmarkReadLaterAdapter
import com.demo.browseseaching.admob.AdType
import com.demo.browseseaching.admob.LoadAdManager
import com.demo.browseseaching.admob.ShowFullAd
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.bean.ReadLaterBean
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.util.LitePalUtil
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_bookmark_readlater.*
import kotlinx.android.synthetic.main.activity_bookmark_readlater.et_search
import kotlinx.android.synthetic.main.activity_bookmark_readlater.iv_back
import kotlinx.android.synthetic.main.activity_bookmark_readlater.refresh_layout
import kotlinx.android.synthetic.main.activity_bookmark_readlater.rv_history
import kotlinx.android.synthetic.main.activity_bookmark_readlater.top_view
import kotlinx.android.synthetic.main.activity_history.*

class BookmarkReadLaterPage:BasePage(R.layout.activity_bookmark_readlater), OnRefreshLoadMoreListener {
    private var currentNum=0
    private var isBook=false
    private var content=""
    private val bookList= arrayListOf<BookmarkBean>()
    private val readList= arrayListOf<ReadLaterBean>()

    private lateinit var showAd:ShowFullAd

    private val bookAdapter by lazy { BookmarkReadLaterAdapter(
        this,
        isBook,
        bookList,
        readList,
        clickItem = {
            EventbusBean(EventbusCode.CLICK_RECORD_ITEM, any = it).send()
            finish()
        }
    )}

    override fun initView() {
        immersionBar.statusBarView(top_view).init()
        isBook = intent.getBooleanExtra("isBook", false)
        tv_title.text=if (isBook) "BookMark" else "Reading list"
        initAd()
        iv_back.setOnClickListener { onBackPressed() }

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
        rv_history.apply {
            layoutManager=LinearLayoutManager(this@BookmarkReadLaterPage)
            adapter=bookAdapter
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        queryList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        queryList()
    }

    private fun queryList(){
        val findAll = if (content.isNullOrEmpty()){
            if (isBook){
                LitePalUtil.queryBookmark(currentNum)
            }else{
                LitePalUtil.queryReadLater(currentNum)
            }
        }else{
            if (isBook){
                LitePalUtil.searchBookmark(currentNum,content)
            }else{
                LitePalUtil.searchReadLater(currentNum,content)
            }
        }

        if (currentNum==0){
            bookList.clear()
            readList.clear()
        }
        currentNum+=findAll.size
        if (isBook){
            bookList.addAll(findAll as List<BookmarkBean>)
        }else{
            readList.addAll(findAll as List<ReadLaterBean>)
        }
        bookAdapter.notifyDataSetChanged()

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


    private fun initAd(){
        val type = if (isBook) AdType.AD_TYPE_BOOKMARK else AdType.AD_TYPE_LATER
        LoadAdManager.check(type)
        showAd= ShowFullAd(this,type){ finish() }
    }

    override fun onBackPressed() {
        if (showAd.hasAdRes()){
            showAd.checkShow {  }
            return
        }
        finish()
    }
}