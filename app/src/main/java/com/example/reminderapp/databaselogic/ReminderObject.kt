package com.example.reminderapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

enum class Importance{
    max, mid, min;

}


@Entity
data class ReminderObject (
    //@PrimaryKey(autoGenerate = true)
   // var id : Int,
    var containsDeadline : Boolean,
    var deadline1 : LocalDateTime,
    var deadline2 : LocalDateTime,
    var deadline3 : LocalDateTime,
    var importance : Importance,
    var category : String)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

    /*
    constructor(
        containsDeadline: Boolean,
        deadline1: LocalDate,
        deadline2: LocalDate,
        deadline3: LocalDate,
        importance: Importance,
        category: String
    ) {
        this.containsDeadline = containsDeadline
        this.deadline1 = deadline1
        this.deadline2 = deadline2
        this.deadline3 = deadline3
        this.importance = importance
        this.category = category
    }

    public fun getContainsDeadline() : Boolean
    {
        return containsDeadline
    }

    public fun getDeadline1() : LocalDate
    {
        return deadline1
    }

    public fun getDeadline2() : LocalDate
    {
        return deadline2
    }

    public fun getDeadline3() : LocalDate
    {
        return deadline3
    }

    public fun getImportance() : Importance
    {
        return importance
    }

    public fun getCategory() : String
    {
        return category
    }
    */

