package com.example.reminderapp.databaselogic

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.reminderapp.ReminderObject

@TypeConverters(ConverterClass::class)
@Database(entities = [ReminderObject::class], version = 3)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun getDao() : DaoInterface
}