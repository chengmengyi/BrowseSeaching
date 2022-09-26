package com.demo.browseseaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.demo.browseseaching.R
import com.demo.browseseaching.manager.BrowseLabelManager
import com.demo.browseseaching.util.printLog
import com.demo.browseseaching.view.BrowseHomeView
import com.demo.browseseaching.view.BrowseWebView
import kotlinx.android.synthetic.main.all_label_item.view.*

class BrowseLabelAdapter(
    private val context: Context,
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
            val browseContentView = BrowseLabelManager.getLabelList()[position]
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

    override fun getItemCount(): Int = BrowseLabelManager.getLabelList().size
}