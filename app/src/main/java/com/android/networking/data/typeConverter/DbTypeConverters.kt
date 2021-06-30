package com.android.networking.data.typeConverter

import androidx.room.TypeConverter
import java.util.*

class DbTypeConverters {

    //class to convert the date into long and long into date

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}