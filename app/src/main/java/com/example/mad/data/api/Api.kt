package com.example.mad.data.api

import com.example.mad.data.api.model.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/signin")
    suspend fun authorization(
        @Body authorization: Authorization
    ):Response<AuthorizationResult>

    @GET("/api/user_qr")
    suspend fun getUserQr():Qr

    @GET("/api/cases")
    suspend fun getCases(): Cases

    @GET("/api/symptoms_history")
    suspend fun getSymptomsHistory(
        @Query("user_id") userId:String
    ):SymptomsHistory
}