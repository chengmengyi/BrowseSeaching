package com.flowerbrowse.unlimited.search.websites.photos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.bean.BrowseHomeFuncBean
import kotlinx.android.synthetic.main.layout_long_click_func_item.view.*

class LongClickAdapter(
    private val ctx:Context,
    private val click:(index:Int)->Unit
):RecyclerView.Adapter<LongClickAdapter.MyView>() {
    var list= arrayListOf<BrowseHomeFuncBean>()
    init {
        list.clear()
        list.add(BrowseHomeFuncBean(R.drawable.open_new_label,"Open a new tab"))
        list.add(BrowseHomeFuncBean(R.drawable.open_new_wuhen_label,"Open a new traceless tab"))
        list.add(BrowseHomeFuncBean(R.drawable.copy_link,"Copy Link"))
        list.add(BrowseHomeFuncBean(R.drawable.share_link,"Share"))
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView =
        MyView(LayoutInflater.from(ctx).inflate(R.layout.layout_long_click_func_item,parent,false))

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseHomeFuncBean = list[position]
            tv_title.text=browseHomeFuncBean.title
            iv_icon.setImageResource(browseHomeFuncBean.icon)
        }
    }

    override fun getItemCount(): Int = list.size

}