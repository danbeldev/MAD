package com.example.mad.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun getDate():String{
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return formatter.format(time)
}
