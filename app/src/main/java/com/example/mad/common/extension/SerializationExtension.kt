package com.example.mad.common.extension

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

inline fun<reified T> T.encodeToString():String{
    return json.encodeToString(this)
}

inline fun<reified T> String.decodeFromString():T{
    return json.decodeFromString(this)
}