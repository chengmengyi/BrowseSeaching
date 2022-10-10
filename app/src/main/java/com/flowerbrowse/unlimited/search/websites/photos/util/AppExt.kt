package com.flowerbrowse.unlimited.search.websites.photos.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.flowerbrowse.unlimited.search.websites.photos.config.Config
import java.lang.Exception
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


fun Context.contact(){
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data= Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, Config.email)
        startActivity(intent)
    }catch (e: Exception){
        toast("Contact us by email：${Config.email}")
    }
}

fun Context.shareApp() {
    val pm = packageManager
    val packageName=pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "https://play.google.com/store/apps/details?id=${packageName}"
    )
    startActivity(Intent.createChooser(intent, "share"))
}


fun Context.updateApp() {
    val packName = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://play.google.com/store/apps/details?id=$packName"
        )
    }
    startActivity(intent)
}