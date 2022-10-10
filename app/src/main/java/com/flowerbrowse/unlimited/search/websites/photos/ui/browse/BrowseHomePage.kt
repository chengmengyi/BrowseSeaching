package com.flowerbrowse.unlimited.search.websites.photos.ui.browse

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.adapter.BrowseBottomAdapter
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusBean
import com.flowerbrowse.unlimited.search.websites.photos.eventbus.EventbusCode
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IUpdateBottomBtnListener
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IUpdateContentViewListener
import com.flowerbrowse.unlimited.search.websites.photos.manager.BrowseLabelManager
import com.flowerbrowse.unlimited.search.websites.photos.manager.SearchEngineManager
import com.flowerbrowse.unlimited.search.websites.photos.base.BasePage
import com.flowerbrowse.unlimited.search.websites.photos.bean.BookmarkBean
import com.flowerbrowse.unlimited.search.websites.photos.dialog.MorePopupWindow
import com.flowerbrowse.unlimited.search.websites.photos.interfaces.IBrowseHomeClickListener
import kotlinx.android.synthetic.main.activity_browse_home.*
import kotlinx.android.synthetic.main.activity_browse_home.top_view
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_browse_bottom.*
import org.greenrobot.eventbus.Subscribe
import com.flowerbrowse.unlimited.search.websites.photos.dialog.SearchDialog
import com.flowerbrowse.unlimited.search.websites.photos.util.printLog


class BrowseHomePage: BasePage(R.layout.activity_browse_home),IUpdateBottomBtnListener, IUpdateContentViewListener,IBrowseHomeClickListener {

    private val bottomAdapter by lazy { BrowseBottomAdapter(this){clickBottom(it)} }

    override fun initEventBus(): Boolean = true

    override fun initView() {
        immersionBar.statusBarView(top_view).init()

        BrowseLabelManager.initView(this,this,this,this)
        addView()
        setAdapter()
        changeTopStatusColor()
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

    private fun showSearchResult(content: String){
        val url = SearchEngineManager.getLoadUrl(content)
        loadUrlInThisLabel(url)
    }

    private fun loadUrlInThisLabel(url: String){
        BrowseLabelManager.getCurrentLabelView().loadUrl(url,removeWeb = true){
            immersionBar
                .statusBarColor(R.color.color_5854BE)
                .init()
        }
        addView()
    }

    private fun removeChild(){
        browse_framelayout.removeAllViews()
        browse_framelayout.removeAllViewsInLayout()
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
            2-> {
                currentLabelView.showHome()
                updateShowContentView()
            }
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
                    changeTopStatusColor()
                }
            }
            2->{
                BrowseLabelManager.addLabel(this,this,this,incognito = true){
                    addView()
                    changeTopStatusColor()
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
            5->openLabelOrReadListPage(true)
            6->openLabelOrReadListPage(false)
            7->openRecent()
            8->openHistoryPage()
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
        changeTopStatusColor()
    }

    override fun deleteContentView() {
        BrowseLabelManager.addLabel(this,this,this){
            addView()
        }
    }

    override fun back() {
        finish()
    }

    override fun openSearch() {
        val searchDialog = SearchDialog()
        searchDialog.show(supportFragmentManager,"SearchDialog")
    }

    override fun loadUrlCurrentLabel(url: String) {
        loadUrlInThisLabel(url)
    }

    override fun openNewLabel(url: String) {
        BrowseLabelManager.addWebLabel(this,url,this,this){
            addView()
            changeTopStatusColor()
        }
    }

    override fun openWebUrlByIncognito(url: String) {
        BrowseLabelManager.addWebLabel(this,url,this,this,incognito = true){
            addView()
            changeTopStatusColor()
        }
    }

    override fun openHistoryPage() {
        startActivity(Intent(this,HistoryPage::class.java))
    }

    override fun openLabelOrReadListPage(isBook: Boolean) {
        startActivity(
            Intent(this,BookmarkReadLaterPage::class.java).apply {
                putExtra("isBook",isBook)
            }
        )
    }

    override fun openRecent() {
        startActivity(Intent(this,RecentPage::class.java))
    }

    private fun changeTopStatusColor(){
        if (BrowseLabelManager.getCurrentLabelView().showingHome()){
            immersionBar
                .statusBarColor(R.color.color_00000000)
                .init()
        }else{
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
                    updateBottomBtnState(updateBack = true, updateMore = true, updateForward = false)
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
            EventbusCode.LOAD_HOME->{
                clickBottom(2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val currentLabelView = BrowseLabelManager.getCurrentLabelView()
        currentLabelView.onDestroy()
    }

}