package com.example.reminderapp.UI.widget


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject


class MyAdapter(context: Context,val layout : Int,var reminders : ArrayList<ReminderObject>) :
     ArrayAdapter<ReminderObject>(context, layout, reminders) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertViewVar = convertView
        if (convertView == null) {
            val inflater = (context as Activity).layoutInflater
            convertViewVar = inflater.inflate(layout, parent, false)
        }
        try {
            val reminderObj = getItem(position)
            val reminderText = reminderObj?.reminderText ?: ""
            val reminderImp = reminderObj?.importance ?: Importance.min
            val containsDeadline = reminderObj?.containsDeadline ?: false
            if(!reminderText.equals("") && !reminderText.equals(R.string.noReminders.toString())) {
                when (reminderImp) {
                    Importance.max -> convertViewVar!!.findViewById<ImageView>(R.id.imageViewListViewRow)
                        .setImageResource(R.drawable.ic_max_importance)
                    Importance.mid -> convertViewVar!!.findViewById<ImageView>(R.id.imageViewListViewRow)
                        .setImageResource(R.drawable.ic_mid_importance)
                    Importance.min -> convertViewVar!!.findViewById<ImageView>(R.id.imageViewListViewRow)
                        .setImageResource(R.drawable.ic_min_importance)
                }
            }else
                convertViewVar!!.findViewById<ImageView>(R.id.imageViewListViewRow).setVisibility(View.GONE)

            convertViewVar.findViewById<TextView>(R.id.textViewReminder).setText(reminderText)

            if(containsDeadline)
            {
                val deadline = reminderObj!!.deadline
                convertViewVar.findViewById<TextView>(R.id.textViewDeadline).setText("Due to: ${deadline.dayOfMonth}.0${deadline.monthValue}.${deadline.year}")
                convertViewVar.findViewById<TextView>(R.id.textViewDeadline).setVisibility(View.VISIBLE)
            }else
            {
                convertViewVar.findViewById<TextView>(R.id.textViewDeadline).setVisibility(View.GONE)
            }



        } catch (e: Exception) {
            e.printStackTrace()
        }

        return convertViewVar!!
    }
}