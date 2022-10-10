package com.flowerbrowse.unlimited.search.websites.photos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.bean.BrowseBottomBean
import com.flowerbrowse.unlimited.search.websites.photos.util.printLog
import kotlinx.android.synthetic.main.layout_browse_bottom_item.view.*

class BrowseBottomAdapter(
    private val context:Context,
    private val clickBottom:(index:Int)->Unit
):RecyclerView.Adapter<BrowseBottomAdapter.MyView>() {
    private val funcList= arrayListOf<BrowseBottomBean>()
    init {
        funcList.clear()
        funcList.add(BrowseBottomBean(R.drawable.icon_left_selected,R.drawable.icon_left_unselect,false))
        funcList.add(BrowseBottomBean(R.drawable.icon_right_selected,R.drawable.icon_right_unselect,false))
        funcList.add(BrowseBottomBean(R.drawable.icon_home_selected,R.drawable.icon_home_unselect,true))
        funcList.add(BrowseBottomBean(R.drawable.icon_label_selected,R.drawable.icon_label_selected,true))
        funcList.add(BrowseBottomBean(R.drawable.icon_more_selected,R.drawable.icon_more_unselect,false))
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                clickBottom.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView =
        MyView(LayoutInflater.from(context).inflate(R.layout.layout_browse_bottom_item,parent,false))

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseBottomBean = funcList[position]
            iv_bottom.setImageResource(if (browseBottomBean.select) browseBottomBean.selectedIcon else browseBottomBean.unSelectIcon)
        }
    }

    override fun getItemCount(): Int = funcList.size

    fun canBack(can:Boolean){
        funcList.first().select=can
        notifyDataSetChanged()
    }

    fun canForward(can:Boolean){
        funcList[1].select=can
        notifyDataSetChanged()
    }

    fun showMore(show:Boolean){
        funcList[4].select=show
        notifyDataSetChanged()
    }

    fun getBottomBtnBean(index:Int)=funcList[index]
}