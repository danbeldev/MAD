package com.example.mad.ui.screens.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mad.common.extension.launchWhenStarted
import com.example.mad.common.getCurrentTime
import com.example.mad.data.api.model.*
import com.example.mad.ui.screens.loginScreen.view.ErrorCard
import com.example.mad.ui.theme.card
import com.example.mad.ui.theme.primaryBackground
import com.example.mad.ui.view.BaseCard
import com.example.mad.ui.view.Image
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    userData:AuthorizationResult
) {
    var userQr:Qr? by remember { mutableStateOf(null) }
    var cases:Cases? by remember { mutableStateOf(null) }
    var history: SymptomsHistory? by remember { mutableStateOf(null) }

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

                    Row {
                        Text(
                            text = "WSA Care",
                            modifier = Modifier
                                .padding(5.dp),
                            fontWeight = FontWeight.W900,
                            fontSize = 23.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        userQr?.data?.let { url ->
                            Image(
                                url = url,
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = getCurrentTime(),
                        modifier = Modifier.padding(5.dp)
                    )

                    if (
                        history != null
                    ){
                        history?.data?.size?.let {
                            repeat(it){ index ->
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
                                        Text(
                                            text = if (
                                                history!!.data[index].probability_infection < 50
                                            ) "CLEAR" else "CALL TO DOCTOR",
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .fillMaxWidth(),
                                            fontWeight = FontWeight.W900,
                                            textAlign = TextAlign.Center
                                        )

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
                                                    text = history!!.data[index].date,
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

                                        androidx.compose.foundation.Image(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = null,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }else {

                        BaseCard(card){
                            Text(
                                text = "No case  in skill area in the last 14 days",
                                modifier = Modifier.padding(15.dp)
                            )
                        }

                        ErrorCard(error = "You haven’t report today’s health status.")

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "${userData.data.name}, \n How are you feeling today?",
                            modifier = Modifier.padding(5.dp)
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