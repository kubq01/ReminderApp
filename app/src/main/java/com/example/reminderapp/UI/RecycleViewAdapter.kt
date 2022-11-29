package com.example.reminderapp.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>() {

    private var reminderList = emptyList<ReminderObject>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val rObject : ReminderObject = reminderList.get(position)

        if(rObject.containsDeadline)
        {
            val deadline : LocalDateTime = rObject.deadline
            val current : LocalDateTime = LocalDateTime.now()
            val start : LocalDateTime = rObject.startDate

            val timeLeft : Duration = Duration.between(current, deadline)
            val timeLeftDays : Long = timeLeft.toDays()

            val timeGiven : Duration = Duration.between(start, deadline)
            val timeGivenDays : Long = timeGiven.toDays()

            val percentage : Double = (timeLeftDays*1.0)/(timeGivenDays*1.0)*100

            if(percentage<33)
            {
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_max_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.maxImp))
            }else if(percentage<66)
            {
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_mid_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.midImp))
            }else
            {
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_min_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.minImp))
            }

            holder.itemView.findViewById<TextView>(R.id.textViewDeadline).setText("Due to: ${rObject.deadline.dayOfYear}.${rObject.deadline.month}.${rObject.deadline.year}")
        }else
        {
            if(rObject.importance.equals(Importance.max))
            {
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_max_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.maxImp))

            }else if(rObject.importance.equals(Importance.mid))
            {
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_mid_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.midImp))

            }else{

                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_min_importance)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setColorFilter(
                    ContextCompat.getColor(holder.itemView.findViewById<ImageView>(R.id.imageView).context,R.color.minImp))

            }

            holder.itemView.findViewById<TextView>(R.id.textViewDeadline).setVisibility(View.GONE)
        }



        holder.itemView.findViewById<TextView>(R.id.textViewReminder).setText(rObject.reminderText)
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    fun addData(newList : List<ReminderObject>){
        reminderList = newList
        notifyDataSetChanged()
    }

}