package com.example.mad.ui.screens.loginScreen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad.common.Constants.SHARED_TOKEN_KEY
import com.example.mad.data.api.model.Authorization
import com.example.mad.data.api.model.AuthorizationResult
import com.example.mad.data.api.repository.ApiRepository
import com.example.mad.data.api.response.Result
import com.example.mad.di.MainApiRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    @MainApiRetrofit private val sharedPreferences: SharedPreferences
):ViewModel() {

    private val _responseAuthorization:MutableStateFlow<Result<AuthorizationResult>?> =
        MutableStateFlow(null)
    val responseAuthorization = _responseAuthorization.asStateFlow()

    fun authorization(
        authorization: Authorization,
        onSuccess:(AuthorizationResult) -> Unit
    ){
        if (checkAuthorization(authorization)){
            apiRepository.authorization(authorization).onEach {
                _responseAuthorization.value = it
                if (it is Result.Success){
                    it.data?.data?.token?.let { token ->
                        saveToken(token)
                        onSuccess(it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun checkAuthorization(
        authorization: Authorization
    ):Boolean{
        if (
            authorization.login.isEmpty()
            || authorization.password.isEmpty()
        ){
            _responseAuthorization.value = Result.Error(
                "is Empty"
            )
            return false
        }

        if (
            !authorization.login.any("."::contains)
            || !authorization.login.any("@"::contains)
        ){
            _responseAuthorization.value = Result.Error(
                "Email @ and ."
            )
            return false
        }

        return true
    }

    private fun saveToken(token:String?){
        sharedPreferences.edit()
            .putString(SHARED_TOKEN_KEY, token)
            .apply()
    }
}