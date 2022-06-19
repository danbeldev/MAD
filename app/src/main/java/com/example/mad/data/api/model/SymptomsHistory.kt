package com.example.mad.data.api.model

data class SymptomsHistory(
    val data:List<SymptomsHistoryItem>
)

data class SymptomsHistoryItem(
    val date:String,
    val probability_infection:Int
)