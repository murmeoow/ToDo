package com.example.taskmanager.data.converters

import androidx.room.TypeConverter
import java.util.Date

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?) : Date? {
      return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toTimestamp(date : Date?): Long? {
        return date?.time
    }
}