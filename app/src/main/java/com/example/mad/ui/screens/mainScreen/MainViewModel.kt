package com.example.mad.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad.data.api.model.SymptomsHistory
import com.example.mad.data.api.model.SymptomsHistoryItem
import com.example.mad.data.api.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository:ApiRepository
):ViewModel() {

    val responseQr = apiRepository.getUserQr()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val responseCases = apiRepository.getCases()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _responseSymptomsHistory:MutableStateFlow<SymptomsHistory?> =
        MutableStateFlow(null)
    val responseSymptomsHistory = _responseSymptomsHistory.asStateFlow().filterNotNull()

    fun getSymptomsHistory(userId:String){
        viewModelScope.launch {
            try {
                val response = apiRepository.getSymptomsHistory(userId)
                _responseSymptomsHistory.value = response
            }catch (e:Exception){}
        }
    }
}