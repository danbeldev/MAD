package com.example.mad.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mad.common.Constants.SHARED_TOKEN_KEY
import com.example.mad.data.api.Api
import com.example.mad.data.api.repository.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "https://wsa2021.mad.hakta.pro"

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun providerApiRepository(
        userApi: Api
    ):ApiRepository = ApiRepository(
        userApi = userApi
    )

    @[Provides Singleton]
    fun providerUserApi(
        @MainApiRetrofit retrofit: Retrofit
    ):Api = retrofit.create(Api::class.java)

    @[Provides Singleton MainApiRetrofit]
    fun providerRetrofit(
        @MainApiRetrofit okHttpClient: OkHttpClient
    ):Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton MainApiRetrofit]
    fun providerOkhttp(
        @MainApiRetrofit sharedPreferences: SharedPreferences
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val token = sharedPreferences.getString(SHARED_TOKEN_KEY,"")

            val request = it.request().newBuilder()
                .addHeader("" +
                        "Authorization", "Bearer $token")
                .build()

            it.proceed(request)
        }
        .build()

    @[Provides Singleton MainApiRetrofit]
    fun providerSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context
        .getSharedPreferences(
            SHARED_TOKEN_KEY,
            Context.MODE_PRIVATE
        )
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainApiRetrofit