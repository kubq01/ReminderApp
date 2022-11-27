package com.example.reminderapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentViewModel(application: Application) : AndroidViewModel(application) {

    //val context : Context = application.applicationContext
    var reminderListlive : LiveData<List<ReminderObject>>
    val servis = ServisClass(getApplication<Application>().applicationContext)

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


}