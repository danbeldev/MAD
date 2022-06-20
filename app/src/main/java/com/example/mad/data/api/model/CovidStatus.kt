package com.example.mad.data.api.model

data class CovidStatus(
    val success:Boolean,
    val data:CovidStatusData
)

data class CovidStatusData(
    val world: CovidStatusItem,
    val current_city: CovidStatusItem,
    val city_before: CovidStatusItem
)

data class CovidStatusItem(
    val infected:Int,
    val death:Int,
    val recovered:Int,
    val vaccinated:Int,
    val recovered_adults:Int,
    val recovered_young:Int
)