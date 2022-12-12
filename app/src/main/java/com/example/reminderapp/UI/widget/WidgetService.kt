package com.example.reminderapp.UI.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.TextView
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import com.example.reminderapp.ServiceClass
import kotlinx.coroutines.*
import java.time.LocalDateTime

class WidgetService(val importance: Importance) : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return RemoteViewFactory(applicationContext,p0!!,importance)
    }

    constructor() : this(Importance.min) {

    }

    private class RemoteViewFactory(val context: Context,val intent : Intent,val importance : Importance) : RemoteViewsService.RemoteViewsFactory{

        lateinit var impList : Deferred<List<ReminderObject>>
        private val scope = CoroutineScope(SupervisorJob())
        private  val service = ServiceClass(context)
        private var size = 1
        private var isEmpty = false

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
            scope.launch(Dispatchers.IO) {
                impList = async { service.getByImportanceStatic(importance) }
                if(impList.await().size==0)
                    isEmpty = true
                else
                    size = impList.await().size

                Log.i("AAAAwidget","widgetService")
            }
        }

        override fun onDataSetChanged() {

        }

        override fun onDestroy() {

        }

        override fun getCount(): Int {
            return size
        }

        override fun getViewAt(p0: Int): RemoteViews {


            val remoteView = RemoteViews(
                context.packageName,
                R.layout.widget_row_layout
            )
            if(isEmpty)
            {
                remoteView.setTextViewText(R.id.textViewReminder,R.string.noReminders.toString())
                remoteView.setViewVisibility(R.id.textViewDeadline,View.GONE)
                remoteView.setViewVisibility(R.id.imageViewListViewRow, View.GONE)
            }else {
                scope.launch(Dispatchers.IO) {
                    remoteView.setTextViewText(
                        R.id.textViewReminder,
                        impList.await().get(p0).reminderText
                    )
                    val reminderObj = impList.await().get(p0)
                    val reminderText = reminderObj.reminderText
                    val reminderImp = reminderObj.importance
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
                }

            }

            return remoteView
        }

        override fun getLoadingView(): RemoteViews {
            val remoteView = RemoteViews(
                context.packageName,
                R.layout.widget_row_layout
            )

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

    }
}