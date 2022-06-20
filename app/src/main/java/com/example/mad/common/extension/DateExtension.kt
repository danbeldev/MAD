package com.example.mad.common.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun getDate():String{
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return formatter.format(time)
}

@SuppressLint("SimpleDateFormat")
fun String.parseDate():String{
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parse = df.parse(this.replace(" UTC", ""))
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm aa")
    return format.format(parse)
}
