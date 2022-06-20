package com.example.mad.widgets.actions

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.mad.data.api.repository.ApiRepository
import com.example.mad.data.api.retrofit.RetrofitInst
import com.example.mad.widgets.QrCodeWidget
import com.example.mad.widgets.QrCodeWidget.Companion.COVID_STATUS_BEFORE_CITY_CASES_KEY
import com.example.mad.widgets.QrCodeWidget.Companion.QR_CODE_URL_KEY
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class GetIndoWidgetAction:ActionCallback {

    private val qrCodeWidget = QrCodeWidget()

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {

        val apiRepository = ApiRepository(userApi = RetrofitInst.api)

        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId){

            it.toMutablePreferences()
                .apply {
                    apiRepository.getUserQr().onEach { qr ->
                        this[stringPreferencesKey(QR_CODE_URL_KEY)] = qr?.data ?: ""
                    }.collect()

                    apiRepository.getCovidStatus().onEach { data ->
                        this[stringPreferencesKey(QrCodeWidget.COVID_STATUS_CITY_VACCINATED_KEY)] =
                            data.data.current_city.vaccinated.toString()
                        this[stringPreferencesKey(QrCodeWidget.COVID_STATUS_CITY_RECOVERED_KEY)] =
                            data.data.current_city.recovered.toString()
                        this[stringPreferencesKey(QrCodeWidget.COVID_STATUS_CITY_CASES_KEY)] =
                            data.data.current_city.infected.toString()
                        this[stringPreferencesKey(COVID_STATUS_BEFORE_CITY_CASES_KEY)] =
                            data.data.city_before.infected.toString()
                    }.collect()
                }
        }

        qrCodeWidget.update(context,glanceId)
    }
}