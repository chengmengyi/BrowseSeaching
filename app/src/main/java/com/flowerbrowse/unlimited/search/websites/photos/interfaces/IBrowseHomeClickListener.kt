package com.flowerbrowse.unlimited.search.websites.photos.interfaces


interface IBrowseHomeClickListener {
    fun back()
    fun openSearch()
    fun loadUrlCurrentLabel(url:String)
    fun openNewLabel(url:String)
    fun openWebUrlByIncognito(url:String)
    fun openHistoryPage()
    fun openLabelOrReadListPage(isBook:Boolean)
    fun openRecent()
}