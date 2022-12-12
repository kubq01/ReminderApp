package com.example.reminderapp.UI.widget


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import com.example.reminderapp.ServiceClass
import kotlinx.coroutines.*
import java.io.Serializable
import java.time.LocalDateTime


class WidgetProvider : AppWidgetProvider() {

    private val scope = CoroutineScope(SupervisorJob())

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for(widget : Int in appWidgetIds) {
            //val service = ServiceClass(context)
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            //val pendingResult = goAsync()

            Log.i("AAAAwidget","widget")


/*
            scope.launch(Dispatchers.IO) {
                val maxImpList = async { service.getByImportanceStatic(Importance.max)}
                val midImpList = async { service.getByImportanceStatic(Importance.mid)}
                val minImpList = async { service.getByImportanceStatic(Importance.min)}

                val nothingToShowList = listOf<ReminderObject>(
                    ReminderObject(R.string.noReminders.toString(),
                        false,
                        LocalDateTime.now(),
                    Importance.min,
                    LocalDateTime.now(),
                    "")
                )

                val intentMax = Intent(context,WidgetService::class.java)
                intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
                views.setRemoteAdapter(R.id.listViewImpMax, intentMax)

                val intentMid = Intent(context,WidgetService::class.java)
                intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
                views.setRemoteAdapter(R.id.listViewImpMid, intentMid)

                val intentMin = Intent(context,WidgetService::class.java)
                intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
                views.setRemoteAdapter(R.id.listViewImpMin, intentMin)

                appWidgetManager.updateAppWidget(widget, views)

                pendingResult.finish()
            }


 */

            val intentMax = Intent(context,WidgetService::class.java)
            intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
            views.setRemoteAdapter(R.id.listViewImpMax, intentMax)

            val intentMid = Intent(context,WidgetService::class.java)
            intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
            views.setRemoteAdapter(R.id.listViewImpMid, intentMid)

            val intentMin = Intent(context,WidgetService::class.java)
            intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
            views.setRemoteAdapter(R.id.listViewImpMin, intentMin)



            appWidgetManager.updateAppWidget(widget, views)



        }
    }
}