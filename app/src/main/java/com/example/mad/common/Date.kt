package com.example.mad.common

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime():String{
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return formatter.format(time)
}