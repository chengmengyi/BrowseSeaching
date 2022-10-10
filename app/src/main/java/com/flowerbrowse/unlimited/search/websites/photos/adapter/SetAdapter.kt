package com.flowerbrowse.unlimited.search.websites.photos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.bean.BrowseHomeFuncBean
import kotlinx.android.synthetic.main.layout_set_item.view.*

class SetAdapter(
    private val ctx:Context,
    private val click:(index:Int)->Unit
):RecyclerView.Adapter<SetAdapter.MyView>() {
    private val list= arrayListOf<BrowseHomeFuncBean>()

    init {
        list.clear()
        list.add(BrowseHomeFuncBean(icon = R.drawable.contact, title = "Contact"))
        list.add(BrowseHomeFuncBean(icon = R.drawable.set_policy, title = "Privacy Policy"))
        list.add(BrowseHomeFuncBean(icon = R.drawable.set_share, title = "Share "))
        list.add(BrowseHomeFuncBean(icon = R.drawable.set_update, title = "Upgrade "))
        list.add(BrowseHomeFuncBean(icon = R.drawable.set_search, title = "Search Engines "))
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(ctx).inflate(R.layout.layout_set_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseHomeFuncBean = list[position]
            iv_title.text=browseHomeFuncBean.title
            iv_icon.setImageResource(browseHomeFuncBean.icon)
        }
    }

    override fun getItemCount(): Int = list.size
}