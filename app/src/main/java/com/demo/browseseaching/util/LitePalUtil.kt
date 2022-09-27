package com.demo.browseseaching.util

import android.content.Context
import android.net.Uri
import com.demo.browseseaching.bean.BookmarkBean
import com.demo.browseseaching.bean.HistoryBean
import com.demo.browseseaching.bean.ReadLaterBean
import com.demo.browseseaching.bean.RecentBean
import org.litepal.LitePal
import java.lang.Exception

class LitePalUtil {
    companion object{

        fun saveHistoryUrl(title:String,url:String){
            try {
                val parse = Uri.parse(url)
                val logo="${parse.scheme}://${parse.host}/favicon.ico"
                HistoryBean(title, url, logo, time = System.currentTimeMillis()).save()
            }catch (e:Exception){

            }
        }

        fun queryHistory(currentNum: Int): List<HistoryBean> {
            return LitePal.select("*")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(HistoryBean::class.java)
        }

        fun searchHistory(currentNum: Int,content:String): List<HistoryBean> {
            return LitePal.select("*")
                .where("title like ? or webUrl like ?", "%$content%", "%$content%")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(HistoryBean::class.java)
        }


        fun saveReadLater(context: Context,title:String,url:String){
            try {
                val parse = Uri.parse(url)
                val logo="${parse.scheme}://${parse.host}/favicon.ico"
                val save = ReadLaterBean(title, url, logo, time = System.currentTimeMillis()).save()
                context.toast(if (save) "Successfully added" else "Add failed")
            }catch (e:Exception){

            }
        }

        fun queryReadLater(currentNum: Int): List<ReadLaterBean> {
            return LitePal.select("*")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(ReadLaterBean::class.java)
        }

        fun searchReadLater(currentNum: Int,content:String): List<ReadLaterBean> {
            return LitePal.select("*")
                .where("title like ? or webUrl like ?", "%$content%", "%$content%")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(ReadLaterBean::class.java)
        }



        fun saveBookmark(context: Context,title:String,url:String){
            try {
                val parse = Uri.parse(url)
                val logo="${parse.scheme}://${parse.host}/favicon.ico"
                val save = BookmarkBean(title, url, logo, time = System.currentTimeMillis()).save()
                context.toast(if (save) "Successfully added" else "Add failed")
            }catch (e:Exception){

            }
        }

        fun queryBookmark(currentNum: Int): List<BookmarkBean> {
            return LitePal.select("*")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(BookmarkBean::class.java)
        }

        fun searchBookmark(currentNum: Int,content:String): List<BookmarkBean> {
            return LitePal.select("*")
                .where("title like ? or webUrl like ?", "%$content%", "%$content%")
                .order("time desc")
                .limit(20)
                .offset(currentNum)
                .find(BookmarkBean::class.java)
        }

        fun saveRecent(title:String,url:String){
            try {
                val parse = Uri.parse(url)
                val logo="${parse.scheme}://${parse.host}/favicon.ico"
                val save = RecentBean(title, url, logo, time = System.currentTimeMillis()).save()
            }catch (e:Exception){

            }
        }

        fun getRecentList():List<RecentBean>{
            return LitePal.select("*")
                .order("time desc")
                .limit(10)
                .offset(0)
                .find(RecentBean::class.java)
        }
    }
}