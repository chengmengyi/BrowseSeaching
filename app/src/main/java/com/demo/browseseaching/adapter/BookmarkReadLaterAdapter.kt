package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.demo.browseseaching.R
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.bean.ReadLaterBean
import com.demo.browseseaching.util.LitePalUtil
import com.demo.browseseaching.view.slidelayout.SlideHelper
import com.demo.browseseaching.view.slidelayout.SlideLayout
import kotlinx.android.synthetic.main.layout_bookmark_item.view.*

class BookmarkReadLaterAdapter(
    private val ctx: Context,
    private val isBook:Boolean,
    private val bookList:ArrayList<BookmarkBean>?=null,
    private val readList:ArrayList<ReadLaterBean>?=null,
    private val clickItem:(bean:BookmarkBean)->Unit
):RecyclerView.Adapter<BookmarkReadLaterAdapter.MyView>() {
    private val mSlideHelper: SlideHelper = SlideHelper()

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        private val slideLayout=view.findViewById<SlideLayout>(R.id.slide_layout)
        private val tvDelete=view.findViewById<AppCompatTextView>(R.id.tv_delete)
        init {
            view.setOnClickListener {
                if (slideLayout.isOpen){
                    slideLayout.close()
                    return@setOnClickListener
                }

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

            tvDelete.setOnClickListener {
                slideLayout.close()
                delete(layoutPosition)
            }
        }
    }

    private fun delete(index:Int){
        if (isBook){
            val deleteBookmark = LitePalUtil.deleteBookmark(bookList!![index])
            if (deleteBookmark==1){
                bookList.removeAt(index)
            }
        }else{
            val deleteReadLater = LitePalUtil.deleteReadLater(readList!![index])
            if (deleteReadLater==1){
                readList.removeAt(index)
            }
        }
        notifyDataSetChanged()
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
            slide_layout.setOpen(bean.isOpen,false)
            slide_layout.setOnStateChangeListener(object : SlideLayout.OnStateChangeListener(){
                override fun onInterceptTouchEvent(layout: SlideLayout?): Boolean {
                    val result = mSlideHelper.closeAll(layout)
                    return false
                }
                override fun onStateChanged(layout: SlideLayout?, open: Boolean) {
                    bean.isOpen=open
                    mSlideHelper.onStateChanged(layout, open)
                }
            })

        }
    }

    override fun getItemCount(): Int = if (isBook) bookList?.size?:0 else readList?.size?:0
}