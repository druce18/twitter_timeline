package by.twitter.util

import java.time.LocalDateTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    private const val TWITTER_FORMAT = "EEE MMM dd HH:mm:ss ZZZ yyyy"
    private const val APP_FORMAT = "dd MMM yyyy"
    private const val REF_FORMAT = "dd MMMM yyyy"
    private const val AVG_DAYS_MONTH = 30
    private const val HOURS_MAX = 24

    fun printDateOnTweet(offsetDateTime: OffsetDateTime): String {
        val dateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        val dateTimeNow = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(APP_FORMAT)

        val timeScreen: String =
                if (dateTimeNow.year > dateTime.year || (dateTimeNow.monthValue - dateTime.monthValue) > 1) {
                    dateTime.format(formatter)

                } else if ((dateTimeNow.monthValue - dateTime.monthValue) == 1) {
                    val monthDay = Month.of(dateTime.monthValue).maxLength()
                    val daysAgo = dateTimeNow.dayOfMonth + (monthDay - dateTime.dayOfMonth)
                    when (daysAgo) {
                        in 1..AVG_DAYS_MONTH -> "${daysAgo}h"
                        else -> dateTime.format(formatter)
                    }

                } else if ((dateTimeNow.dayOfMonth - dateTime.dayOfMonth) > 1) {
                    "${dateTimeNow.dayOfMonth - dateTime.dayOfMonth}d"

                } else if ((dateTimeNow.dayOfMonth - dateTime.dayOfMonth) == 1) {
                    val hoursAgo = dateTimeNow.hour + (HOURS_MAX - dateTime.hour)
                    when (hoursAgo) {
                        in 1..HOURS_MAX -> "${hoursAgo}h"
                        else -> "yesterday"
                    }

                } else if ((dateTimeNow.hour - dateTime.hour) > 0) {
                    "${dateTimeNow.hour - dateTime.hour}h"

                } else {
                    "${dateTimeNow.minute - dateTime.minute}m"
                }

        return timeScreen
    }

    fun getDateReg(dateString: String): String {
        val dateTime = getOffsetDateTime(dateString).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern(REF_FORMAT)
        return dateTime.format(formatter)
    }

    fun getOffsetDateTime(dateString: String): OffsetDateTime {
        val formatter = DateTimeFormatter.ofPattern(TWITTER_FORMAT, Locale.ENGLISH)
        val offsetDateTime = OffsetDateTime.parse(dateString, formatter)
        return offsetDateTime
    }

}