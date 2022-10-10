package com.demo.browseseaching.ui.server

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.browseseaching.R
import com.demo.browseseaching.adapter.ChooseServerAdapter
import com.demo.browseseaching.base.BasePage
import com.demo.browseseaching.bean.ServerBean
import com.demo.browseseaching.server.ServerConnectManager
import kotlinx.android.synthetic.main.activity_choose_server.*

class ChooseServerPage:BasePage(R.layout.activity_choose_server) {
    override fun initView() {
        immersionBar.statusBarView(top).init()
        iv_back.setOnClickListener {

        }

        recycler_view.apply {
            layoutManager=LinearLayoutManager(this@ChooseServerPage)
            adapter=ChooseServerAdapter(this@ChooseServerPage){
                click(it)
            }
        }
    }

    private fun click(serverBean: ServerBean){
        val currentServer = ServerConnectManager.getCurrentServer()
        val connected = ServerConnectManager.connected()
        if (connected&&currentServer.host!=serverBean.host){
            AlertDialog.Builder(this).apply {
                setMessage("You are currently connected and need to disconnect before manually connecting to the server.")
                setPositiveButton("sure") { _, _ ->
                    back("disconnect",serverBean)
                }
                setNegativeButton("cancel",null)
                show()
            }
        }else{
            if (connected){
                back("",serverBean)
            }else{
                back("connect",serverBean)
            }
        }
    }

    private fun back(action:String,serverBean: ServerBean){
        ServerConnectManager.setCurrentServer(serverBean)
        setResult(300, Intent().apply {
            putExtra("action",action)
        })
        finish()
    }
}