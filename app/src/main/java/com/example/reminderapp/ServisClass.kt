package com.example.reminderapp

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.reminderapp.databaselogic.DaoInterface

class ServisClass(var context : Context) {

    //private val repository : RepositoryClass = RepositoryClass(context)
    val dao : DaoInterface = RepositoryClass(context).getDataBase().getDao()

    suspend fun insertReminder(reminder : ReminderObject)
    {
        dao.insertReminder(reminder)
    }

     fun showAll() : LiveData<List<ReminderObject>>
    {
        return dao.showAll()
    }

    suspend fun deleteAll()
    {
        dao.deleteAll()
    }

}