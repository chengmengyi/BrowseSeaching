package com.flowerbrowse.unlimited.search.websites.photos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.flowerbrowse.unlimited.search.websites.photos.R
import com.flowerbrowse.unlimited.search.websites.photos.manager.BrowseLabelManager
import com.flowerbrowse.unlimited.search.websites.photos.util.printLog
import com.flowerbrowse.unlimited.search.websites.photos.view.BrowseContentView
import com.flowerbrowse.unlimited.search.websites.photos.view.BrowseHomeView
import com.flowerbrowse.unlimited.search.websites.photos.view.BrowseWebView
import kotlinx.android.synthetic.main.all_label_item.view.*

class BrowseLabelAdapter(
    private val context: Context,
    private val list:ArrayList<BrowseContentView>,
    private val click:(index:Int)->Unit,
    private val deleteLabel:(index:Int)->Unit,
): RecyclerView.Adapter<BrowseLabelAdapter.MyView>(){

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        private val delete=view.findViewById<AppCompatImageView>(R.id.iv_close_label)
        init {
            view.setOnClickListener {
                click.invoke(layoutPosition)
            }
            delete.setOnClickListener {
                deleteLabel.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(context).inflate(R.layout.all_label_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val browseContentView = list[position]
            val showView = browseContentView.getShowView()
            if (showView is BrowseHomeView){
                val homeBitmap = BrowseLabelManager.homeBitmap
                if (null!=homeBitmap){
                    iv_label.setImageBitmap(homeBitmap)
                }
            }else if (showView is BrowseWebView){
                val webViewBitmap = showView.getWebViewBitmap()
                if (null!=webViewBitmap){
                    iv_label.setImageBitmap(webViewBitmap)
                }
            }

        }
    }

    override fun getItemCount(): Int = list.size
}