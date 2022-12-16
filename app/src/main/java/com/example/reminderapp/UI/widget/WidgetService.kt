package com.example.reminderapp.UI.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.LiveData
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import com.example.reminderapp.ServiceClass
import kotlinx.coroutines.*
import java.time.LocalDateTime


class WidgetService() : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return RemoteViewsFactory(applicationContext,p0!!)
    }


/*
    private class RemoteViewFactory(val context: Context,val intent : Intent) : RemoteViewsService.RemoteViewsFactory{

        private var impList = ArrayList<ReminderObject>()
        private lateinit var liveImp : LiveData<List<ReminderObject>>
        private lateinit var impType : String
        private val scope = CoroutineScope(SupervisorJob())
        private  val service = ServiceClass(context)




        val nothingToShowList = listOf<ReminderObject>(
            ReminderObject(
                R.string.noReminders.toString(),
                false,
                LocalDateTime.now(),
                Importance.min,
                LocalDateTime.now(),
                "")
        )



        override fun onCreate() {


            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            Log.d("AAAAAAAppWidgetId", appWidgetId.toString())



            impList.add(ReminderObject(
                R.string.noReminders.toString(),
                false,
                LocalDateTime.now(),
                Importance.min,
                LocalDateTime.now(),
                ""))
            impList.add(ReminderObject(
                R.string.noReminders.toString(),
                false,
                LocalDateTime.now(),
                Importance.min,
                LocalDateTime.now(),
                ""))
            impType = intent.getStringExtra("imp").toString()

            liveImp = service.showByimportance(Importance.valueOf(impType))
            liveImp.observeForever{
                newData(it as ArrayList<ReminderObject>)
            }

                Log.i("AAAAwidget","widgetServiceccc $impType")

        }

        override fun onDataSetChanged() {

            liveImp = service.showByimportance(Importance.valueOf(impType))

            if(impList.isEmpty())
                impList.add(ReminderObject(
                    R.string.noReminders.toString(),
                    false,
                    LocalDateTime.now(),
                    Importance.min,
                    LocalDateTime.now(),
                    ""))

            Log.i("AAAAwidget","data changed")

           for(item  in 0 until count)
                getViewAt(item)


        }

        override fun onDestroy() {

        }

        override fun getCount(): Int {
            return impList.size
        }

        override fun getViewAt(p0: Int): RemoteViews {

            Log.i("AAAAwidget","widgetServicevvv $p0 size: $count type $impType")


            val remoteView = RemoteViews(
                context.packageName,
                R.layout.widget_row_layout
            )


            val reminderObj = impList[p0]
            val reminderText = reminderObj.reminderText
            val reminderImp = reminderObj.importance//Importance.valueOf(impType)
            val containsDeadline = reminderObj.containsDeadline


            when (reminderImp) {
                Importance.max -> remoteView.setImageViewResource(R.id.imageViewListViewRow,R.drawable.ic_max_importance)
                Importance.mid -> remoteView.setImageViewResource(R.id.imageViewListViewRow,R.drawable.ic_mid_importance)
                Importance.min -> remoteView.setImageViewResource(R.id.imageViewListViewRow,R.drawable.ic_min_importance)
            }

            remoteView.setViewVisibility(R.id.imageViewListViewRow,View.VISIBLE)

            remoteView.setTextViewText(R.id.textViewReminder,reminderText)

            if (containsDeadline) {
                 val deadline = reminderObj.deadline
                 remoteView.setTextViewText(R.id.textViewDeadline,"Due to: ${deadline.dayOfMonth}.0${deadline.monthValue}.${deadline.year}")
                 remoteView.setViewVisibility(R.id.textViewDeadline,View.VISIBLE)
            } else {
                 remoteView.setViewVisibility(R.id.textViewDeadline,View.GONE)
            }


            return remoteView
        }

        override fun getLoadingView(): RemoteViews {
            val remoteView = RemoteViews(
                context.packageName,
                R.layout.widget_row_layout
            )
            remoteView.setTextViewText(R.id.textViewReminder, "Loading")
            return remoteView
        }

        override fun getViewTypeCount(): Int {
          return 1
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        private fun newData(list : ArrayList<ReminderObject>)
        {
            impList.clear()
            impList.addAll(list)
            Log.i("AAAAwidgetSize","${impList.size}")
            onDataSetChanged()


        }

    }

 */
}