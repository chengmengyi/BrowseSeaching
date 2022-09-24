package com.demo.browseseaching.ui.browse

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.BrowseBottomAdapter
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import com.demo.browseseaching.interfaces.IUpdateBottomBtnListener
import com.demo.browseseaching.interfaces.IUpdateContentViewListener
import com.demo.browseseaching.manager.BrowseLabelManager
import com.demo.browseseaching.manager.SearchEngineManager
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.dialog.MorePopupWindow
import com.demo.browseseaching.interfaces.IBrowseHomeClickListener
import com.demo.browseseaching.util.printLog
import kotlinx.android.synthetic.main.activity_browse_home.*
import kotlinx.android.synthetic.main.activity_browse_home.top_view
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_browse_bottom.*
import org.greenrobot.eventbus.Subscribe
import android.view.View
import android.view.ViewGroup
import com.demo.browseseaching.view.BrowseHomeView
import com.demo.browseseaching.view.BrowseWebView


class BrowseHomePage: BasePage(R.layout.activity_browse_home),IUpdateBottomBtnListener, IUpdateContentViewListener,IBrowseHomeClickListener {

    private val bottomAdapter by lazy { BrowseBottomAdapter(this){clickBottom(it)} }

    override fun initEventBus(): Boolean = true

    override fun initView() {
        immersionBar.statusBarView(top_view).init()

        BrowseLabelManager.initView(this,this,this,this)
        addView()
        setAdapter()
    }

    private fun addView(){
        removeChild()
        browse_framelayout.addView(BrowseLabelManager.getCurrentLabelView().getShowView())
    }

    private fun setAdapter(){
        rc_browse_bottom.apply {
            layoutManager=GridLayoutManager(this@BrowseHomePage,5)
            adapter=bottomAdapter
        }
        updateBottomBtnState(updateBack = true, updateMore = true)
    }

    private fun showSearchResult(content:String){
        val url = SearchEngineManager.getLoadUrl(content)
        BrowseLabelManager.getCurrentLabelView().loadUrl(url){
            immersionBar
                .statusBarColor(R.color.color_5854BE)
                .init()
        }
        addView()
    }

    private fun removeChild(){
        browse_framelayout.removeAllViews()
//        browse_framelayout.removeAllViewsInLayout()
//        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
////        browse_framelayout.removeView(currentLabelView.getShowView())
//        currentLabelView.onDestroy()
    }

    private fun clickBottom(index:Int){
        val bottomBtnBean = bottomAdapter.getBottomBtnBean(index)
        if (!bottomBtnBean.select){
            return
        }
        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
        when(index){
            0-> currentLabelView.clickBottomBack()
            1-> currentLabelView.clickBottomFroward()
            2-> currentLabelView.showHome()
            3-> {
                BrowseLabelManager.getHomeBitmap()
                startActivity(Intent(this,AllLabelPage::class.java))
            }
            4->{
                val basePopupWindow = MorePopupWindow(this){
                    clickMoreFunc(it)
                }
                basePopupWindow.show(bottom)
            }
        }
    }

    private fun clickMoreFunc(index:Int){
        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
        when(index){
            0->{
                if (!currentLabelView.showingHome()){
                    currentLabelView.reloadUrl()
                }
            }
            1->{
                BrowseLabelManager.addLabel(this,this,this){
                    addView()
                }
            }
            2->{
                BrowseLabelManager.addLabel(this,this,this,incognito = true){
                    addView()
                }
            }
            3->{
                if (!currentLabelView.showingHome()){
                    currentLabelView.addReadLater()
                }
            }
            4->{
                if (!currentLabelView.showingHome()){
                    currentLabelView.addBookmark()
                }
            }
        }
    }

    override fun updateBottomBtnState(updateBack: Boolean?, updateForward: Boolean?, updateMore: Boolean?) {
        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
        if (null!=updateBack){
            bottomAdapter.canBack(!currentLabelView.showingHome())
        }
        if (null!=updateForward){
            if (currentLabelView.showingHome()){
                bottomAdapter.canForward(currentLabelView.hasWebView())
            }else{
                bottomAdapter.canForward(updateForward)
            }
        }
        if (null!=updateMore){
            bottomAdapter.showMore(!currentLabelView.showingHome())
        }
    }

    override fun updateShowContentView() {
        addView()
        if (BrowseLabelManager.getCurrentLabelView().showingHome()){
            immersionBar
                .statusBarColor(R.color.color_00000000)
                .init()
        }
    }

    override fun deleteContentView() {
        BrowseLabelManager.addLabel(this,this,this){
            addView()
        }
    }

    override fun back() {
        finish()
    }

    override fun openNewLabel(url: String) {
        BrowseLabelManager.addWebLabel(this,url,this,this){
            addView()
            immersionBar.statusBarColor(R.color.color_5854BE).init()
        }
    }

    override fun openWebUrlByIncognito(url: String) {
        BrowseLabelManager.addWebLabel(this,url,this,this,incognito = true){
            addView()
            immersionBar.statusBarColor(R.color.color_5854BE).init()
        }
    }

    @Subscribe
    fun onEvent(bean: EventbusBean) {
        when(bean.code){
            EventbusCode.SEARCH_URL->{
                showSearchResult(bean.str)
            }
            EventbusCode.ADD_LABEL->{
                BrowseLabelManager.addLabel(this,this,this,incognito = bean.boolean){
                    addView()
                }
            }
            EventbusCode.CHANGE_SHOW_CONTENT->{
                addView()
            }
            EventbusCode.CLICK_RECORD_ITEM->{
                if (null!=bean.any && bean.any is BookmarkBean){
                    val bookmarkBean = bean.any as BookmarkBean
                    openNewLabel(bookmarkBean.webUrl)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        removeChild()
        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
//        browse_framelayout.removeView(currentLabelView.getShowView())
        currentLabelView.onDestroy()
    }

}