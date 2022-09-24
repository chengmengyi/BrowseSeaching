package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.manager.SearchEngineManager
import com.demo.browseseaching.util.showInvisible
import kotlinx.android.synthetic.main.layout_search_engines_item.view.*

class SearchEnginesAdapter(
    private val ctx:Context,
    private val click:(index:Int)->Unit
):RecyclerView.Adapter<SearchEnginesAdapter.MyView>() {

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(ctx).inflate(R.layout.layout_search_engines_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val searchEngineBean = SearchEngineManager.getEngineList()[position]
            tv_title.text=searchEngineBean.title
            tv_content.text=searchEngineBean.url
            iv_logo.setImageResource(searchEngineBean.icon)
            val currentEngine = SearchEngineManager.getCurrentEngine()
            val selected = currentEngine.title == searchEngineBean.title
            root_layout.isSelected=selected
            iv_gou.showInvisible(selected)
        }
    }

    override fun getItemCount(): Int = SearchEngineManager.getEngineList().size
}