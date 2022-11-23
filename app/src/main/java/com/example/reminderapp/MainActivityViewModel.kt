package com.example.reminderapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(startingCount : Int, var context: Context) : ViewModel() {


    fun showAll() : LiveData<List<ReminderObject>>
    {
        return ServisClass(context).showAll()
    }

    fun insertReminder(reminder : ReminderObject)
    {
        viewModelScope.launch(Dispatchers.IO){
            ServisClass(context).insertReminder(reminder)
        }
    }

    fun deleteAll()
    {
        viewModelScope.launch(Dispatchers.IO) {
            ServisClass(context).deleteAll()
        }
    }


}