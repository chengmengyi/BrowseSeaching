package com.demo.browseseaching.bean

class ConfAdBean(
    val id_flower:String,
    val type_flower:String,
    val sort_flower:Int,
    val source_flower:String,
) {
    override fun toString(): String {
        return "ConfAdBean(id_flower='$id_flower', type_flower='$type_flower', sort_flower=$sort_flower, source_flower='$source_flower')"
    }
}