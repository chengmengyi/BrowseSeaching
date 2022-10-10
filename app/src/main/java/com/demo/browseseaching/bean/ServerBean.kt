package com.demo.browseseaching.bean

import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager

class ServerBean(
    val host:String="",
    val port:Int=0,
    val pwd:String="",
    val country:String="",
    val city:String="",
    val method:String=""
) {

    fun createProfile(){
        val profile = Profile(
            id = 0L,
            name = "$country - $city",
            host = host,
            remotePort = port,
            password = pwd,
            method = method
        )

        var id:Long?=null
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.remotePort==profile.remotePort&&it.host==profile.host){
                id=it.id
                return@forEach
            }
        }
        if (null==id){
            ProfileManager.createProfile(profile)
        }else{
            profile.id=id!!
            ProfileManager.updateProfile(profile)
        }
    }

    fun getServerID():Long{
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.host==host&&it.remotePort==port){
                return it.id
            }
        }
        return 0L
    }

    fun isFastServer()=country=="Smart Servers"&&host.isEmpty()
}