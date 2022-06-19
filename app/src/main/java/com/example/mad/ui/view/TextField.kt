package com.example.mad.ui.view


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mad.ui.theme.primaryBackground
import com.example.mad.ui.theme.tintColor

@Composable
fun TextFieldBase(
    modifier: Modifier = Modifier,
    label:String,
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(5.dp),
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = tintColor,
            backgroundColor = primaryBackground,
            cursorColor = tintColor,
            focusedLabelColor = tintColor
        ), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    placeholder:String,
    onValue:(String) -> Unit,
    onClose:() -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onValue(it)
        },
        shape = AbsoluteRoundedCornerShape(5.dp),
        placeholder = { Text(
            text = placeholder
        ) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onClose()
                text = ""
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = tintColor,
            backgroundColor = primaryBackground,
            cursorColor = tintColor,
            focusedLabelColor = tintColor
        ), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), modifier = modifier
            .clip(AbsoluteRoundedCornerShape(15.dp))
    )
}

@Composable
fun TextFieldNumber(
    modifier: Modifier = Modifier,
    label:String,
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(5.dp),
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = tintColor,
            backgroundColor = primaryBackground,
            cursorColor = tintColor,
            focusedLabelColor = tintColor
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            keyboardType = KeyboardType.Number
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldEmail(
    modifier: Modifier = Modifier,
    label:String = "Email",
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = {
            Text(text = label)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = tintColor,
            backgroundColor = primaryBackground,
            cursorColor = tintColor,
            focusedLabelColor = tintColor
        ), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldPassword(
    modifier: Modifier = Modifier,
    label:String = "Password",
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = {
            Text(text = label)
        }, shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = tintColor,
            backgroundColor = primaryBackground,
            cursorColor = tintColor,
            focusedLabelColor = tintColor
        ), visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go
        ), modifier = modifier.padding(5.dp)
    )
}