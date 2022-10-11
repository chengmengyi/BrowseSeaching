package com.demo.browseseaching.ui

import android.content.Intent
import android.os.Handler
import com.demo.browseseaching.R
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.config.ReadFirebase
import com.demo.browseseaching.dialog.ServerTipsDialog
import com.demo.browseseaching.point.PointUtil
import com.demo.browseseaching.ui.browse.BrowseHomePage
import com.demo.browseseaching.ui.server.ServerPage
import kotlinx.android.synthetic.main.activity_home.*

class HomePage: BasePage(R.layout.activity_home) {
    override fun initView() {
        immersionBar.statusBarView(top_view).init()

        llc_browse.setOnClickListener {
            startActivity(Intent(this, BrowseHomePage::class.java))
        }

        iv_set.setOnClickListener {
            startActivity(Intent(this, SettingPage::class.java))
        }

        llc_server.setOnClickListener {
            startActivity(Intent(this, ServerPage::class.java))
        }

        Handler().postDelayed({showDialog()},500)
    }

    private fun showDialog(){
        if (canShowDialog()){
            val serverTipsDialog = ServerTipsDialog()
            serverTipsDialog.show(supportFragmentManager,"ServerTipsDialog")
        }
    }

    private fun canShowDialog():Boolean{
        if (ReadFirebase.dialogStatus=="0"){
            return true
        }else if (ReadFirebase.dialogStatus=="1"){
            return PointUtil.installReferrer.contains("facebook")||PointUtil.installReferrer.contains("fb4a")
        }
        return false
    }
}