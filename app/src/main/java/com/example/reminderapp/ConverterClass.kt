package com.example.reminderapp

import androidx.room.TypeConverter
import java.time.*
import java.util.*

class ConverterClass {
    @TypeConverter
    fun dateToLong(date : LocalDateTime) : Long
    {
        val zdt: ZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
        return zdt.toInstant().toEpochMilli()
    }

    @TypeConverter
    fun LongtoDate(longDate : Long) : LocalDateTime
    {
       return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate), TimeZone.getDefault().toZoneId())
    }

    @TypeConverter
    fun enumToString(importance : Importance) : String
    {
        return importance.name
    }

    @TypeConverter
    fun stringToEnum(name : String) : Importance
    {
        return Importance.valueOf(name)
    }
}