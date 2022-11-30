package com.example.reminderapp.databaselogic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.reminderapp.ReminderObject

@Dao
interface DaoInterface {

    @Insert
    suspend fun insertReminder(reminder : ReminderObject)

    @Query("Select * from ReminderObject")
    fun showAll() : LiveData<List<ReminderObject>>

    @Query("Delete from ReminderObject")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(reminder : ReminderObject)

    @Query("Select category from ReminderObject group by category")
    suspend fun getAllCategories() : List<String>

}