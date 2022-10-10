package com.flowerbrowse.unlimited.search.websites.photos.util

import android.content.Context
import android.net.Uri
import com.flowerbrowse.unlimited.search.websites.photos.bean.BookmarkBean
import com.flowerbrowse.unlimited.search.websites.photos.bean.HistoryBean
import com.flowerbrowse.unlimited.search.websites.photos.bean.ReadLaterBean
import com.flowerbrowse.unlimited.search.websites.photos.bean.RecentBean
import org.litepal.LitePal
import java.lang.Exception

class LitePalUtil {
    companion object{

        fun saveHistoryUrl(title:String,url:String){
            try {
                val find = LitePal.select("*")
                    .where("webUrl = ?", url)
                    .limit(200)
                    .offset(0)
                    .find(HistoryBean::class.java)
                if (find?.isEmpty() == true){
                    val parse = Uri.parse(url)
                    val logo="${parse.scheme}://${parse.host}/favicon.ico"
                    HistoryBean(title, url, logo, time = System.currentTimeMillis()).save()
                }
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
                val find = LitePal.select("*")
                    .where("webUrl = ?", url)
                    .order("time desc")
                    .limit(200)
                    .offset(0)
                    .find(ReadLaterBean::class.java)
                if (find?.isEmpty() == true){
                    val parse = Uri.parse(url)
                    val logo="${parse.scheme}://${parse.host}/favicon.ico"
                    val save = ReadLaterBean(title, url, logo, time = System.currentTimeMillis()).save()
                    context.toast(if (save) "Successfully added" else "Add failed")
                }
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

        fun deleteReadLater(bean: ReadLaterBean): Int {
            return LitePal.deleteAll(ReadLaterBean::class.java, "time = ?", "${bean.time}")
        }

        fun saveBookmark(context: Context,title:String,url:String){
            try {
                val find = LitePal.select("*")
                    .where("webUrl = ?", url)
                    .order("time desc")
                    .limit(200)
                    .offset(0)
                    .find(BookmarkBean::class.java)
                if (find?.isEmpty() == true){
                    val parse = Uri.parse(url)
                    val logo="${parse.scheme}://${parse.host}/favicon.ico"
                    val save = BookmarkBean(title, url, logo, time = System.currentTimeMillis()).save()
                    context.toast(if (save) "Successfully added" else "Add failed")
                }
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

        fun deleteBookmark(bean: BookmarkBean): Int {
            return LitePal.deleteAll(BookmarkBean::class.java, "time = ?", "${bean.time}")
        }

        fun saveRecent(title:String,url:String){
            try {
                val find = LitePal.select("*")
                    .where("webUrl = ?", url)
                    .order("time desc")
                    .limit(200)
                    .offset(0)
                    .find(RecentBean::class.java)
                if (find?.isEmpty() == true){
                    val parse = Uri.parse(url)
                    val logo="${parse.scheme}://${parse.host}/favicon.ico"
                    val save = RecentBean(title, url, logo, time = System.currentTimeMillis()).save()
                }
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