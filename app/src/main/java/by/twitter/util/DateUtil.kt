package by.twitter.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun toSimpleString(dateString: String): String {
            val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
            val simpleDateFormat = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
            val date = simpleDateFormat.parse(dateString)
            val appFormat = "dd-MM-yyy"
            val dateFormat = SimpleDateFormat(appFormat, Locale.ENGLISH)
            return dateFormat.format(date)
        }
    }
}