package com.demo.browseseaching.manager

import com.demo.browseseaching.R
import com.demo.browseseaching.bean.SearchEngineBean
import com.demo.browseseaching.mmkv.MMKVKey
import com.demo.browseseaching.mmkv.MMKVUtil

object SearchEngineManager {
    private var index=0
    private val engineList= arrayListOf<SearchEngineBean>()

    fun init() {
        engineList.clear()
        engineList.add(SearchEngineBean("Bing", R.drawable.bing,"https://www.bi/**/ng.com/search?q="))
        engineList.add(SearchEngineBean("Google",R.drawable.google,"https://www.google.com/search?client=mysearch&ie=UTF-8&oe=UTF-8&q="))
        engineList.add(SearchEngineBean("Yahoo",R.drawable.yahoo,"https://search.yahoo.com/search?p="))
        engineList.add(SearchEngineBean("Duck",R.drawable.duck,"https://duckduckgo.com/?t=mysearch&q="))

        index = MMKVUtil.read(MMKVKey.SEARCH_ENGINE)
        if (index==-1){
            index=3
        }
    }

    fun getCurrentEngine()=engineList[index]

    fun getLoadUrl(content:String):String{
        var url=content
        var isUrl = content.lowercase().startsWith("http")
        if (!isUrl){
            url="${getCurrentEngine().url}$url"
        }
        return url
    }

    fun getEngineList()=engineList

    fun setIndex(index:Int){
        if (this.index!=index){
            this.index=index
            MMKVUtil.writeInt(MMKVKey.SEARCH_ENGINE,index)
        }
    }
}