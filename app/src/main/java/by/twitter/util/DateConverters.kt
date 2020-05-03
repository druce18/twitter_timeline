package by.twitter.util

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateConverters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME


    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String): OffsetDateTime {
        return formatter.parse(value, OffsetDateTime::from)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: OffsetDateTime): String {
        return date.format(formatter)
    }

}