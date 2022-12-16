package com.example.reminderapp.UI.widget


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import com.example.reminderapp.ServiceClass
import kotlinx.coroutines.*


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

                val impListMax = async { service.getByImportanceStatic(Importance.max) }
                val impListMid = async { service.getByImportanceStatic(Importance.mid) }
                val impListMin = async { service.getByImportanceStatic(Importance.min) }




                val intentMax = Intent(context,WidgetService::class.java)
                intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
                intentMax.putExtra("impList",impListMax.await() as ArrayList<ReminderObject>)
                views.setRemoteAdapter(R.id.listViewImpMax, intentMax)



                val intentMid = Intent(context,WidgetService::class.java)
                intentMid.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMid.setData(Uri.parse(intentMid.toUri(Intent.URI_INTENT_SCHEME)))
                intentMid.putExtra("impList",impListMid.await() as ArrayList<ReminderObject>)
                views.setRemoteAdapter(R.id.listViewImpMid, intentMid)

                val intentMin = Intent(context,WidgetService::class.java)
                intentMin.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
                intentMin.setData(Uri.parse(intentMin.toUri(Intent.URI_INTENT_SCHEME)))
                intentMin.putExtra("impList",impListMin.await() as ArrayList<ReminderObject>)
                views.setRemoteAdapter(R.id.listViewImpMin, intentMin)

                appWidgetManager.updateAppWidget(widget, views)

                Log.i("AAAAwidget","widgetEndProvider")

                pendingResult.finish()
            }

             */




            val intentMax = Intent(context,WidgetService::class.java)
            intentMax.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMax.setData(Uri.parse(intentMax.toUri(Intent.URI_INTENT_SCHEME)))
            intentMax.putExtra("imp","max")
            views.setRemoteAdapter(R.id.listViewImpMax, intentMax)

            val intentMid = Intent(context,WidgetService::class.java)
            intentMid.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMid.setData(Uri.parse(intentMid.toUri(Intent.URI_INTENT_SCHEME)))
            intentMid.putExtra("imp","mid")
            views.setRemoteAdapter(R.id.listViewImpMid, intentMid)

            val intentMin = Intent(context,WidgetService::class.java)
            intentMin.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget)
            intentMin.setData(Uri.parse(intentMin.toUri(Intent.URI_INTENT_SCHEME)))
            intentMin.putExtra("imp","min")
            views.setRemoteAdapter(R.id.listViewImpMin, intentMin)

            appWidgetManager.updateAppWidget(widget, views)

        }
    }
}