package com.flowerbrowse.unlimited.search.websites.photos.manager

import android.content.Context
import android.view.View
import com.flowerbrowse.unlimited.search.websites.photos.dialog.LongClickHomeFuncPopup

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