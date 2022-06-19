package com.example.mad.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseButton(
    onClick:() -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = Modifier.padding(20.dp)
            .size(
                180.dp,50.dp
            ),
        shape = AbsoluteRoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        onClick = { onClick() }
    ) { content() }
}