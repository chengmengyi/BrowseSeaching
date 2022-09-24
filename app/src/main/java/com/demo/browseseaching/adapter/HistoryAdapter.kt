package com.demo.browseseaching.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.HistoryBean
import com.demo.browseseaching.util.printLog
import kotlinx.android.synthetic.main.layout_history_content.view.*
import kotlinx.android.synthetic.main.layout_history_title.view.*

class HistoryAdapter(
    private val ctx:Context,
    private val list:ArrayList<HistoryBean>,
    private val clickItem:(bean:HistoryBean)->Unit
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==10){
            return ContentVH(LayoutInflater.from(ctx).inflate(R.layout.layout_history_content,parent,false))
        }
        return TitleVH(LayoutInflater.from(ctx).inflate(R.layout.layout_history_title,parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val historyBean = list[position]
        with(holder.itemView){
            if (holder is TitleVH){
                tv_title.text=historyBean.title
            }
            if (holder is ContentVH){
                tv_web_title.text="${historyBean.title}--${historyBean.time}"
                tv_web_url.text=historyBean.webUrl
                Glide.with(ctx)
                    .load(historyBean.iconUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .into(iv_cover)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = list[position].itemType


    internal class TitleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tag = true
        }
    }

    inner class ContentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.tag = false
            itemView.setOnClickListener {
                clickItem.invoke(list[layoutPosition])
            }
        }
    }
}