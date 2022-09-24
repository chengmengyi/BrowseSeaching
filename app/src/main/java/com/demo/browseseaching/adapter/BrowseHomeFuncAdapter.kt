package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.BrowseHomeFuncBean
import kotlinx.android.synthetic.main.layout_browse_home_func_item.view.*

class BrowseHomeFuncAdapter(
    private val context:Context,
    private val clickFunc:(funcBean:BrowseHomeFuncBean)->Unit,
    private var longClickFunc:(view:View,funcBean:BrowseHomeFuncBean)->Unit
):RecyclerView.Adapter<BrowseHomeFuncAdapter.MyView>() {
    private val funcList= arrayListOf<BrowseHomeFuncBean>()
    init {
        funcList.clear()
//        funcList.add(BrowseHomeFuncBean(R.drawable.dailymotion,"instagram",index=0, webUrl = "https://www.instagram.com/"))
        funcList.add(BrowseHomeFuncBean(R.drawable.dailymotion,"instagram",index=0, webUrl = "https://www.baidu.com/"))
        funcList.add(BrowseHomeFuncBean(R.drawable.facebook,"Facebook",index=1, webUrl = "https://www.facebook.com/"))
        funcList.add(BrowseHomeFuncBean(R.drawable.netflix222,"Netfilx",index=2, webUrl = "https://www.netflix.com/"))
        funcList.add(BrowseHomeFuncBean(R.drawable.gmail,"Youtube", index = 3, webUrl = "https://www.youtube.com/"))
        funcList.add(BrowseHomeFuncBean(R.drawable.shuqian,"Bookmarks",index=4))
        funcList.add(BrowseHomeFuncBean(R.drawable.liebiao,"Read list",index=5))
        funcList.add(BrowseHomeFuncBean(R.drawable.biaoqian,"Recent",index=6))
        funcList.add(BrowseHomeFuncBean(R.drawable.lishijilu,"History",index=7))
    }

    inner class MyView (view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                clickFunc.invoke(funcList[layoutPosition])
            }
            view.setOnLongClickListener {
                longClickFunc.invoke(view,funcList[layoutPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView =
        MyView(LayoutInflater.from(context).inflate(R.layout.layout_browse_home_func_item,parent,false))

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseHomeFuncBean = funcList[position]
            tv_func.text=browseHomeFuncBean.title
            iv_func.setImageResource(browseHomeFuncBean.icon)
        }
    }

    override fun getItemCount(): Int = funcList.size
}