package com.example.reminderapp

import android.content.Context
import androidx.room.Room
import com.example.reminderapp.databaselogic.DatabaseClass


class RepositoryClass(var context : Context) {


    private var database : DatabaseClass? = null


    fun getDataBase(): DatabaseClass {

        if(database==null) {
          database = Room.databaseBuilder(
                context.applicationContext, DatabaseClass::
                class.java, "database"
            ).fallbackToDestructiveMigration().build()
        }
            return database as DatabaseClass
    }


}