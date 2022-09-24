package com.demo.browseseaching.interfaces

interface IUpdateBottomBtnListener {
    fun updateBottomBtnState(
        updateBack:Boolean?=null,
        updateForward:Boolean?=null,
        updateMore:Boolean?=null,
    )
}