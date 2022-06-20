package com.example.mad.data.api.retrofit

import com.example.mad.data.api.Api
import com.example.mad.di.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInst {

    companion object{
        val api: Api by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}