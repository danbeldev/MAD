package com.example.mad.widgets

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.example.mad.widgets.view.QrCodeWidgetView

class QrCodeWidgetReceiver(
    override val glanceAppWidget: GlanceAppWidget = QrCodeWidget()
) :GlanceAppWidgetReceiver()

class QrCodeWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        QrCodeWidgetView()
    }

    companion object{
        const val QR_CODE_URL_KEY = "qr_code_url"
        const val COVID_STATUS_CITY_CASES_KEY = "covid_status_city_cases_key"
        const val COVID_STATUS_BEFORE_CITY_CASES_KEY = "covid_status_before_city_cases_key"
        const val COVID_STATUS_CITY_VACCINATED_KEY = "covid_status_city_vaccinated_key"
        const val COVID_STATUS_CITY_RECOVERED_KEY = "covid_status_city_recovered_key"
    }
}