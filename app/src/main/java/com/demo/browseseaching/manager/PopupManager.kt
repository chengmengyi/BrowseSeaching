package com.demo.browseseaching.manager

import android.content.Context
import android.view.View
import com.demo.browseseaching.dialog.LongClickHomeFuncPopup

object PopupManager {
    private var longClickPopup:LongClickHomeFuncPopup?=null

    fun showLongClickPopup(ctx:Context,view:View,click:(index:Int)->Unit){
        if (null==longClickPopup){
            longClickPopup=LongClickHomeFuncPopup(ctx)
        }
        longClickPopup?.showPopup(view){
            click.invoke(it)
        }
    }
}