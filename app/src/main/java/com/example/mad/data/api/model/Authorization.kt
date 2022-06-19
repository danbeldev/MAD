package com.example.mad.data.api.model

import kotlinx.serialization.Serializable

data class Authorization(
    val login:String,
    val password:String
)

@Serializable
data class AuthorizationResult(
    val success:Boolean?,
    val error:String?,
    val message:String?,
    val title:String?,
    val data:AuthorizationData
)

@Serializable
data class AuthorizationData(
    val token:String,
    val id:String,
    val login:String,
    val name:String
)