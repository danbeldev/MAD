package com.example.mad.ui.screens.loginScreen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mad.common.extension.launchWhenStarted
import com.example.mad.data.api.model.Authorization
import com.example.mad.data.api.model.AuthorizationResult
import com.example.mad.data.api.response.Result
import com.example.mad.ui.theme.primaryBackground
import com.example.mad.ui.view.TextFieldEmail
import com.example.mad.ui.view.TextFieldPassword
import kotlinx.coroutines.flow.onEach
import com.example.mad.R
import com.example.mad.common.ConnectionInternet
import com.example.mad.navigation.Screen
import com.example.mad.ui.screens.loginScreen.view.ErrorCard
import com.example.mad.ui.view.BaseButton

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    application:Application
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    val login = remember { mutableStateOf("healthy@wsa.com") }
    val password = remember { mutableStateOf("1234") }

    var authorizationResult:Result<AuthorizationResult>? by remember { mutableStateOf(null) }

    val connection = ConnectionInternet(application = application)

    viewModel.responseAuthorization.onEach {
        authorizationResult = it
    }.launchWhenStarted()

    connection.observe(lifecycleOwner){
        authorizationResult = if (it){
            null
        }else {
            Result.Error(message = "No connect internet")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Sign in",
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.W900,
                fontSize = 23.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "if you don’t have account credentials, you can take it in" +
                    " near hospital in your city after vaccination",
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextFieldEmail(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AbsoluteRoundedCornerShape(10.dp))
                    .padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    ),
                label = "Your login",
                value = login
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextFieldPassword(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AbsoluteRoundedCornerShape(10.dp))
                    .padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    ),
                label = "Your password",
                value = password
            )

            AnimatedVisibility(
                visible = authorizationResult != null
            ) {
                ErrorCard(
                    authorizationResult?.message
                        ?: authorizationResult?.data?.message ?: ""
                )
            }

            BaseButton(
                onClick = {
                    viewModel.authorization(
                        Authorization(
                            login = login.value,
                            password = password.value
                        )
                    ){navController.navigate(Screen.Main.data(it))}
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sign in",
                        modifier = Modifier.padding(5.dp),
                        color = primaryBackground
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.forward),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}