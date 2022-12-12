package com.example.reminderapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class FragmentViewModel(application: Application) : AndroidViewModel(application) {

    //val context : Context = application.applicationContext
    var reminderListlive : LiveData<List<ReminderObject>>
    val servis = ServiceClass(getApplication<Application>().applicationContext)

     init{
         reminderListlive = servis.showAll()
     }


    fun showAll() : LiveData<List<ReminderObject>>
    {
        return servis.showAll()
    }

    fun insertReminder(reminder : ReminderObject)
    {
        viewModelScope.launch(Dispatchers.IO){
            servis.insertReminder(reminder)
        }
    }

    fun deleteAll()
    {
        viewModelScope.launch(Dispatchers.IO) {
            servis.deleteAll()
        }
    }

    fun delete(reminder: ReminderObject)
    {
        viewModelScope.launch(Dispatchers.IO) {
            servis.delete(reminder)
        }
    }

    suspend fun getAllCategories() : List<String>
    {
            return servis.getAllCategories()

    }

    fun updateReminder(reminder: ReminderObject)
    {
        viewModelScope.launch(Dispatchers.IO) {
            servis.updateReminder(reminder)
        }
    }

    fun showByCategories(cat : String) : LiveData<List<ReminderObject>>
    {
        return servis.showByCategories(cat)
    }

    fun showByImportance(imp : Importance) : LiveData<List<ReminderObject>>
    {
        return servis.showByimportance(imp)
    }

    fun updateData()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val reminderList = servis.getAllStatic()
            for(r : ReminderObject in reminderList)
            {
                if(r.containsDeadline)
                {
                    val current : LocalDateTime = LocalDateTime.now()

                    val timeLeft : Duration = Duration.between(current, r.deadline)
                    val timeLeftDays : Long = timeLeft.toDays()

                    val timeGiven : Duration = Duration.between(r.startDate, r.deadline)
                    val timeGivenDays : Long = timeGiven.toDays()

                    val percentage : Double = (timeLeftDays*1.0)/(timeGivenDays*1.0)*100

                    if(percentage<33)
                    {
                        r.importance = Importance.max
                    }else if(percentage<66)
                    {
                        r.importance = Importance.mid
                    }else
                    {
                        r.importance = Importance.min
                    }

                    servis.updateReminder(r)
                }
            }
        }
    }


}