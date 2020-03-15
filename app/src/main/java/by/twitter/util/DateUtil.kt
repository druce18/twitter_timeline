package by.twitter.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun toSimpleString(date: Date): String {
            val format = SimpleDateFormat("dd-MM-yyy")
            return format.format(date)
        }
    }
}