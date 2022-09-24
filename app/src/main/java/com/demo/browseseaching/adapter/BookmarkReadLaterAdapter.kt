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
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.bean.ReadLaterBean
import kotlinx.android.synthetic.main.layout_history_content.view.*

class BookmarkReadLaterAdapter(
    private val ctx: Context,
    private val isBook:Boolean,
    private val bookList:ArrayList<BookmarkBean>?=null,
    private val readList:ArrayList<ReadLaterBean>?=null,
    private val clickItem:(bean:BookmarkBean)->Unit
):RecyclerView.Adapter<BookmarkReadLaterAdapter.MyView>() {

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                if (isBook){
                    clickItem.invoke(bookList!![layoutPosition])
                }else{
                    val readLaterBean = readList!![layoutPosition]
                    clickItem.invoke(
                        BookmarkBean(
                            title = readLaterBean.title,
                            webUrl = readLaterBean.webUrl,
                            time = readLaterBean.time,
                            iconUrl = readLaterBean.iconUrl
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView =
        MyView(LayoutInflater.from(ctx).inflate(R.layout.layout_bookmark_item,parent,false))

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val bean=if (isBook){
                bookList!![position]
            }else{
                val readLaterBean = readList!![position]
                BookmarkBean(
                    title = readLaterBean.title,
                    webUrl = readLaterBean.webUrl,
                    iconUrl = readLaterBean.iconUrl,
                )
            }
            tv_web_title.text="${bean.title}"
            tv_web_url.text=bean.webUrl
            Glide.with(ctx)
                .load(bean.iconUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(iv_cover)
        }
    }

    override fun getItemCount(): Int = if (isBook) bookList?.size?:0 else readList?.size?:0
}