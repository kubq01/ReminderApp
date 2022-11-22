package com.example.reminderapp

import java.time.LocalDate

enum class Importance{
    max, mid, min
}

class ReminderObject {
    private var containsDeadline : Boolean = false
    private var deadline1 : LocalDate = LocalDate.of(2000, 1, 1)
    private var deadline2 : LocalDate = LocalDate.of(2000, 1, 1)
    private var deadline3 : LocalDate = LocalDate.of(2000, 1, 1)
    private var importance : Importance = Importance.max
    private var category : String = ""

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


}