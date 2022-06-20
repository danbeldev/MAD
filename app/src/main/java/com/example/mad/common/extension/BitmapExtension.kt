package com.example.mad.common.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.executeBlocking
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.ByteArrayOutputStream
import com.example.mad.R

fun String?.coilToBitmap(
    context: Context
):Bitmap {
    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .crossfade(true)
        .error(R.drawable.ic_refresh)
        .build()
    val result = (loading.executeBlocking(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}

fun Int.decodeResourceBitmap(context: Context):Bitmap{
    return BitmapFactory.decodeResource(context.resources,this)
}

fun ByteArray.decodeByteArrayBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Bitmap.toByteArray():ByteArray{
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)
    return outputStream.toByteArray()
}