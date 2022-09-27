package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.RecentBean
import kotlinx.android.synthetic.main.layout_history_content.view.*

class RecentAdapter(
    private val ctx:Context,
    private val list:List<RecentBean>,
    private val click:(bean:RecentBean)->Unit

):RecyclerView.Adapter<RecentAdapter.MyView>() {

    inner class MyView (view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(list[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(ctx).inflate(R.layout.layout_recent_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val recentBean = list[position]
            tv_web_title.text="${recentBean.title}"
            tv_web_url.text=recentBean.webUrl
            Glide.with(ctx)
                .load(recentBean.iconUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(iv_cover)
        }
    }

    override fun getItemCount(): Int = list.size
}