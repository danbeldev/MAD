package com.example.mad.data.api.repository

import android.util.Log
import com.example.mad.data.api.Api
import com.example.mad.data.api.model.*
import com.example.mad.data.api.response.BaseApiResponse
import com.example.mad.data.api.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val userApi: Api
):BaseApiResponse() {

    fun authorization(
        authorization:Authorization
    ):Flow<Result<AuthorizationResult>> = flow {
        emit( safeApiCall { userApi.authorization(authorization) } )
    }

    fun getUserQr():Flow<Qr?> = flow {
        try {
            emit( userApi.getUserQr() )
        }catch (e:Exception){ Log.e("Retrofit", e.message.toString()) }
    }

    fun getCases():Flow<Cases?> = flow {
        try {
            emit( userApi.getCases() )
        }catch (e:Exception){}
    }

    suspend fun getSymptomsHistory(userId:String): SymptomsHistory? =
        userApi.getSymptomsHistory(userId)
}