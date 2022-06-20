package com.example.mad.ui.screens.mainScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mad.R
import com.example.mad.common.ConnectionInternet
import com.example.mad.common.extension.getDate
import com.example.mad.common.extension.launchWhenStarted
import com.example.mad.common.extension.parseDate
import com.example.mad.common.getCurrentTime
import com.example.mad.data.api.model.*
import com.example.mad.ui.screens.loginScreen.view.ErrorCard
import com.example.mad.ui.theme.card
import com.example.mad.ui.theme.primaryBackground
import com.example.mad.ui.view.BaseButton
import com.example.mad.ui.view.BaseCard
import com.example.mad.ui.view.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    userData:AuthorizationResult,
    application: Application
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var userQr:Qr? by remember { mutableStateOf(null) }
    var cases:Cases? by remember { mutableStateOf(null) }
    var history: SymptomsHistory? by remember { mutableStateOf(null) }

    val connection = ConnectionInternet(application = application)
    var connectionCheck  by remember { mutableStateOf(true) }

    var isVisible by remember { mutableStateOf(false) }

    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = "text/plain"

    LaunchedEffect(key1 = true) {
        delay(600L)
        isVisible = true
    }
    
    connection.observe(lifecycleOwner){
        connectionCheck = it
    }
    
    viewModel.responseQr.onEach {
        userQr = it
    }.launchWhenStarted()

    viewModel.responseCases.onEach {
        cases = it
    }.launchWhenStarted()

    viewModel.getSymptomsHistory(userData.data.id)
    viewModel.responseSymptomsHistory.onEach {
        Log.e("Retrosdasd", it.toString())
        history = it
    }.launchWhenStarted()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(content = {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "WSA Care",
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.Center),
                            fontWeight = FontWeight.W900,
                            fontSize = 23.sp,
                            textAlign = TextAlign.Center
                        )

                        userQr?.data?.let { url ->
                            Image(
                                url = url,
                                modifier = Modifier
                                    .size(60.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(5.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = getCurrentTime(),
                        modifier = Modifier.padding(5.dp)
                    )

                    if (!connectionCheck){
                        ErrorCard(error = "No connect internet")
                    }

                    if (
                        history != null
                    ){
                        history?.data?.size?.let {
                            repeat(it){ index ->
                                AnimatedVisibility(visible = isVisible) {
                                    Card(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxWidth(),
                                        backgroundColor = card,
                                        shape = AbsoluteRoundedCornerShape(20.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(
                                                        if (history!!.data[index].probability_infection < 70)
                                                            Color.Green
                                                        else
                                                            Color.Red)
                                            ) {
                                                Text(
                                                    text = if (
                                                        history!!.data[index].probability_infection < 70
                                                    ) "CLEAR" else "CALL TO DOCTOR",
                                                    modifier = Modifier
                                                        .padding(5.dp)
                                                        .fillMaxWidth(),
                                                    fontWeight = FontWeight.W900,
                                                    textAlign = TextAlign.Center
                                                )

                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(15.dp)
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text(
                                                        text = "Name",
                                                        modifier = Modifier.padding(5.dp),
                                                        fontWeight = FontWeight.W900
                                                    )

                                                    Text(
                                                        text = userData.data.name,
                                                        modifier = Modifier.padding(5.dp)
                                                    )
                                                }

                                                Column {
                                                    Text(
                                                        text = "Date and Time",
                                                        modifier = Modifier.padding(5.dp),
                                                        fontWeight = FontWeight.W900
                                                    )

                                                    Text(
                                                        text = history!!.data[index].date.parseDate(),
                                                        modifier = Modifier.padding(5.dp)
                                                    )
                                                }
                                            }

                                            Divider()

                                            Text(
                                                text = "* Wear mask.  Keep 2m distance.  Wash hands.",
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Start
                                            )

                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .clickable {
                                                        intent.putExtra(Intent.EXTRA_TEXT, "" +
                                                                if (history!!.data[index].probability_infection < 70)
                                                                    "As of ${getDate().parseDate()}, it is likely that I am healthy (but I’m not sure)"
                                                                else
                                                                    "As of ${getDate().parseDate()}, there is a possibility that I have a covid"
                                                        )

                                                        context.startActivities(
                                                            listOf(
                                                                Intent.createChooser(intent, "")
                                                            ).toTypedArray()
                                                        )
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(50.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.chek_ok),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(5.dp)
                                .size(60.dp),
                            tint = Color.White
                        )

                        Text(
                            text = "You have checked in today.",
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Re-check in",
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline
                        )

                    }else {

                        ErrorCard(error = "You haven’t report today’s health status.")

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "${userData.data.name}, \n How are you feeling today?",
                            modifier = Modifier.padding(5.dp)
                        )

                        BaseButton(onClick = { /*TODO*/ }) {
                            Text(
                                text = "Check in now ->",
                                color = primaryBackground
                            )
                        }

                        Text(
                            text = "Why do this?",
                            modifier = Modifier.padding(5.dp),
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }

            item {
                if (cases?.data == 0){
                    BaseCard(color = card) {
                        Text(text = "No case  in skill area in the last 14 days")
                    }
                }
            }
        })
    }
}