package com.example.mad.navigation

import com.example.mad.common.extension.encodeToString
import com.example.mad.data.api.model.AuthorizationResult

const val USER_DATA = "user_data"

sealed class Screen(val route:String) {
    object Login:Screen("login_screen")
    object Main:Screen("main_screen?user_data={user_data}"){
        fun data(
            user_data:AuthorizationResult
        ):String = "main_screen?user_data=${user_data.encodeToString()}"
    }
}