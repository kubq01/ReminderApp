package com.example.reminderapp.databaselogic

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.reminderapp.Importance
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

    @Update
    suspend fun updateReminder(reminder: ReminderObject)

    @Query("Select * from ReminderObject where category = :cat")
    fun showByCategories(cat : String) : LiveData<List<ReminderObject>>

    @Query("Select * from ReminderObject where importance = :imp")
    fun showByImportance(imp : Importance) : LiveData<List<ReminderObject>>

}