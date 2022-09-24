package com.demo.browseseaching.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


fun printLog(string: String){
    Log.e("qwer",string)
}

fun View.show(show:Boolean){
    visibility=if (show) View.VISIBLE else View.GONE
}

fun View.showInvisible(show:Boolean){
    visibility=if (show) View.VISIBLE else View.INVISIBLE
}

fun transTimeToStr(time:Long)= SimpleDateFormat("yyyy-MM-dd").format(Date(time))

fun Context.toast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}

fun Context.copyText(text: String){
    val cm= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText(null, text))
    toast("Copy succeeded")
}

fun Context.shareUrl(url:String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, url)
    startActivity(Intent.createChooser(intent, "share"))
}
