package com.rijaldev.mygithub.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun String.getTimeAgo(): String {
        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")

        val dateBefore = sdf.parse(this)
        val pastTime = dateBefore?.time as Long
        val diff = Calendar.getInstance().time.time - pastTime

        val oneSec = 1000L
        val oneMin = 60 * oneSec
        val oneHour: Long = 60 * oneMin
        val oneDay: Long = 24 * oneHour
        val oneMonth: Long = 30 * oneDay
        val oneYear: Long = 365 * oneDay

        val diffMin: Long = diff / oneMin
        val diffHours: Long = diff / oneHour
        val diffDays: Long = diff / oneDay
        val diffMonths: Long = diff / oneMonth
        val diffYears: Long = diff / oneYear

        return when {
            diffYears > 0 -> "${diffYears}y ago"
            diffMonths > 0 -> "${(diffMonths - diffYears / 12)}mo ago"
            diffDays > 0 -> "${(diffDays - diffMonths / 30)}d ago"
            diffHours > 0 -> "${(diffHours - diffDays * 24)}h ago"
            diffMin > 0 -> "${(diffMin - diffHours * 60)}m ago"
            else -> "Just now"
        }
    }
}