package com.example.mad.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad.common.extension.decodeFromString
import com.example.mad.navigation.Screen
import com.example.mad.navigation.USER_DATA
import com.example.mad.ui.screens.loginScreen.LoginScreen
import com.example.mad.ui.screens.mainScreen.MainScreen

@Composable
fun MainNavHost() {

   val navHostController = rememberNavController()

   NavHost(
       navController = navHostController,
       startDestination = Screen.Login.route,
       builder = {
           composable(Screen.Login.route){
               LoginScreen(
                   navController = navHostController
               )
           }
           composable(
               Screen.Main.route,
               arguments = listOf(
                   navArgument(USER_DATA){
                       type = NavType.StringType
                   }
               )
           ){
               MainScreen(
                   userData = it.arguments!!.getString(USER_DATA)!!.decodeFromString()
               )
           }
       }
   )
}