package com.example.mad.widgets.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.mad.common.extension.coilToBitmap
import com.example.mad.widgets.QrCodeWidget.Companion.QR_CODE_URL_KEY
import com.example.mad.widgets.actions.GetIndoWidgetAction
import com.example.mad.R
import com.example.mad.widgets.QrCodeWidget.Companion.COVID_STATUS_BEFORE_CITY_CASES_KEY
import com.example.mad.widgets.QrCodeWidget.Companion.COVID_STATUS_CITY_CASES_KEY
import com.example.mad.widgets.QrCodeWidget.Companion.COVID_STATUS_CITY_RECOVERED_KEY
import com.example.mad.widgets.QrCodeWidget.Companion.COVID_STATUS_CITY_VACCINATED_KEY

@Composable
fun QrCodeWidgetView() {

    val context = LocalContext.current
    val prefs = currentState<Preferences>()
    val qrCode = prefs[stringPreferencesKey(QR_CODE_URL_KEY)]

    val cases = prefs[stringPreferencesKey(COVID_STATUS_CITY_CASES_KEY)]
    val casesBefore = prefs[stringPreferencesKey(COVID_STATUS_BEFORE_CITY_CASES_KEY)]
    val vaccinated = prefs[stringPreferencesKey(COVID_STATUS_CITY_VACCINATED_KEY)]
    val recovered = prefs[stringPreferencesKey(COVID_STATUS_CITY_RECOVERED_KEY)]

    Column {
        Box(
            modifier = GlanceModifier
                .background(ImageProvider(R.drawable.widget_primary_background))
                .clickable(actionRunCallback<GetIndoWidgetAction>())
                .appWidgetBackground(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (qrCode != null){
                        Image(
                            provider = ImageProvider(qrCode.coilToBitmap(context)),
                            contentDescription = null,
                            modifier = GlanceModifier
                                .size(155.dp)
                        )
                    }else if (cases == null || vaccinated == null || recovered == null ) {
                        Image(
                            provider = ImageProvider(R.drawable.ic_refresh),
                            contentDescription = null,
                            modifier = GlanceModifier
                                .size(155.dp)
                        )
                    }

                    if (cases != null || vaccinated != null || recovered != null){
                        Box(
                            modifier = GlanceModifier
                                .size(155.dp)
                                .background(ImageProvider(
                                    if ((casesBefore ?: "0").toInt() > (cases ?: "0").toInt())
                                        R.drawable.widget_secondary_background_red
                                    else
                                        R.drawable.widget_secondary_background_green
                                ))
                                .defaultWeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = "+$cases casesin your city",
                                    modifier = GlanceModifier
                                        .padding(5.dp),
                                    style = TextStyle(
                                        color = ColorProvider(Color.White)
                                    )
                                )

                                Text(
                                    text = "+$vaccinated vaccinated",
                                    modifier = GlanceModifier
                                        .padding(5.dp),
                                    style = TextStyle(
                                        color = ColorProvider(Color.White)
                                    )
                                )

                                Text(
                                    text = "+$recovered recovered",
                                    modifier = GlanceModifier
                                        .padding(5.dp),
                                    style = TextStyle(
                                        color = ColorProvider(Color.White)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}