package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.BrowseHomeFuncBean
import kotlinx.android.synthetic.main.layout_more_func_item.view.*

class MoreFuncAdapter(
    private val context: Context,
    private val clickItem:(index:Int)->Unit
):RecyclerView.Adapter<MoreFuncAdapter.MyView>() {
    var list= arrayListOf<BrowseHomeFuncBean>()
    init {
        list.clear()
        list.add(BrowseHomeFuncBean(R.drawable.reload,"Reload"))
        list.add(BrowseHomeFuncBean(R.drawable.open_new_label,"Open a new tab"))
        list.add(BrowseHomeFuncBean(R.drawable.open_new_wuhen_label,"Open a new traceless tab"))
        list.add(BrowseHomeFuncBean(R.drawable.add_late_read,"Add Read Later"))
        list.add(BrowseHomeFuncBean(R.drawable.add_label,"add bookmark"))
        list.add(BrowseHomeFuncBean(R.drawable.label,"bookmark"))
        list.add(BrowseHomeFuncBean(R.drawable.read_list,"Reading list"))
        list.add(BrowseHomeFuncBean(R.drawable.open_recent_label,"Recently opened tabs"))
        list.add(BrowseHomeFuncBean(R.drawable.history,"History"))
//        list.add(BrowseHomeFuncBean(R.drawable.more_set,"Setting"))
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                clickItem.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView =
        MyView(LayoutInflater.from(context).inflate(R.layout.layout_more_func_item,parent,false))

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseHomeFuncBean = list[position]
            tv_title.text=browseHomeFuncBean.title
            iv_icon.setImageResource(browseHomeFuncBean.icon)
        }
    }

    override fun getItemCount(): Int = list.size
}