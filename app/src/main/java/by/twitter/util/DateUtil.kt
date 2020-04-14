package by.twitter.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    private const val TWITTER_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    private const val APP_FORMAT = "dd MMM yyyy"
    private const val AVG_DAYS_MONTH = 30

    fun toSimpleString(dateString: String): String {
        val simpleDateFormat = SimpleDateFormat(TWITTER_FORMAT, Locale.ENGLISH)
        val date = simpleDateFormat.parse(dateString)
        val dateTime = LocalDateTime.ofInstant(date!!.toInstant(), ZoneId.systemDefault())
        val dateTimeNow = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(APP_FORMAT)

        val timeScreen: String =
                if (dateTimeNow.year > dateTime.year || (dateTimeNow.monthValue - dateTime.monthValue) > 1) {
                    dateTime.format(formatter)

                } else if ((dateTimeNow.monthValue - dateTime.monthValue) == 1) {
                    val monthDay = Month.of(dateTime.monthValue).maxLength()
                    val daysAgo = dateTimeNow.dayOfMonth + (monthDay - dateTime.dayOfMonth)
                    if (daysAgo > AVG_DAYS_MONTH) {
                        dateTime.format(formatter)
                    } else {
                        "${daysAgo}d"
                    }

                } else if ((dateTimeNow.dayOfMonth - dateTime.dayOfMonth) > 1) {
                    "${dateTimeNow.dayOfMonth - dateTime.dayOfMonth}d"

                } else if ((dateTimeNow.dayOfMonth - dateTime.dayOfMonth) == 1) {
                    val hoursAgo = dateTimeNow.hour + (24 - dateTime.hour)
                    if (hoursAgo > 24) {
                        "yesterday"
                    } else {
                        "${hoursAgo}h"
                    }

                } else if ((dateTimeNow.hour - dateTime.hour) > 0) {
                    "${dateTimeNow.hour - dateTime.hour}h"

                } else {
                    "${dateTimeNow.minute - dateTime.minute}m"
                }

        return timeScreen
    }

}