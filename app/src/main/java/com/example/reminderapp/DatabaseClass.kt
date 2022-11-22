package com.example.reminderapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(ConverterClass::class)
@Database(entities = [ReminderObject::class], version = 1)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun getDao() : DaoInterface
}