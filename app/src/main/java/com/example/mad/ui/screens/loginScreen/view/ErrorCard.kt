package com.example.mad.ui.screens.loginScreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mad.ui.theme.errorCard
import com.example.mad.R

@Composable
fun ErrorCard(
    error:String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 15.dp,
                vertical = 5.dp
            ),
        backgroundColor = errorCard,
        shape = AbsoluteRoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = error,
                color = Color.White,
                modifier = Modifier.padding(5.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.info),
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp).
                        size(25.dp)
            )

        }
    }
}