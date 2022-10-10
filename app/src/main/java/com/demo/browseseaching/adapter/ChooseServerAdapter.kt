package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.ServerBean
import com.demo.browseseaching.server.ServerConnectManager
import com.demo.browseseaching.server.ServerInfoManager
import com.demo.browseseaching.util.getServerLogo
import kotlinx.android.synthetic.main.layout_server_item.view.*

class ChooseServerAdapter(
    private val context:Context,
    private val click:(bean:ServerBean)->Unit
):RecyclerView.Adapter<ChooseServerAdapter.MyView>() {
    private val list= arrayListOf<ServerBean>()
    init {
        list.add(ServerInfoManager.createFastServer())
        list.addAll(ServerInfoManager.getServerList())
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(list[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(context).inflate(R.layout.layout_server_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val serverBean = list[position]
            tv_server_country.text=serverBean.country
            iv_server_logo.setImageResource(getServerLogo(serverBean.country))
            item_layout.isSelected=serverBean.host==ServerConnectManager.getCurrentServer().host
        }
    }

    override fun getItemCount(): Int = list.size
}