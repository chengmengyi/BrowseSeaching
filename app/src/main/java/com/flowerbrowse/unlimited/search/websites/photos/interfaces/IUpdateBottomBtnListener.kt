package com.flowerbrowse.unlimited.search.websites.photos.interfaces

interface IUpdateBottomBtnListener {
    fun updateBottomBtnState(
        updateBack:Boolean?=null,
        updateForward:Boolean?=null,
        updateMore:Boolean?=null,
    )
}