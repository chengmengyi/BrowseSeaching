package com.demo.browseseaching.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.demo.browseseaching.R
import com.demo.browseseaching.config.Config
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun printLog(string: String){
    Log.e("qwer",string)
}

fun getServerLogo(country:String)=when(country){
    else-> R.drawable.fast
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


fun createNumKey(type:String): String {
    return try {
        "$type...${SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))}"
    } catch (e: Exception) {
        type
    }
}


fun Context.getNetStatus(): Int {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return 2
        } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return 0
        }
    } else {
        return 1
    }
    return 1
}