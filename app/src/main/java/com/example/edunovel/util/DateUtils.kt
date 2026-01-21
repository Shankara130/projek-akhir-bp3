package com.example.edunovel.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {
    
    private const val DATE_FORMAT_DEFAULT = "dd MMM yyyy"
    private const val DATE_FORMAT_FULL = "dd MMMM yyyy, HH:mm"
    private const val TIME_FORMAT = "HH:mm"
    
    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis()
    }
    
    fun formatDate(timestamp: Long, pattern: String = DATE_FORMAT_DEFAULT): String {
        val sdf = SimpleDateFormat(pattern, Locale("id", "ID"))
        return sdf.format(Date(timestamp))
    }
    
    fun formatDateFull(timestamp: Long): String {
        return formatDate(timestamp, DATE_FORMAT_FULL)
    }
    
    fun formatTime(timestamp: Long): String {
        return formatDate(timestamp, TIME_FORMAT)
    }
    
    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "Baru saja"
            diff < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                "$minutes menit yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                "$hours jam yang lalu"
            }
            diff < TimeUnit.DAYS.toMillis(7) -> {
                val days = TimeUnit.MILLISECONDS.toDays(diff)
                "$days hari yang lalu"
            }
            else -> formatDate(timestamp)
        }
    }
    
    fun getDaysDifference(startTimestamp: Long, endTimestamp: Long): Long {
        val diff = endTimestamp - startTimestamp
        return TimeUnit.MILLISECONDS.toDays(diff)
    }
    
    fun isToday(timestamp: Long): Boolean {
        val today = Date()
        val date = Date(timestamp)
        val todayFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return todayFormat.format(today) == todayFormat.format(date)
    }
    
    fun isYesterday(timestamp: Long): Boolean {
        val yesterday = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1))
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return format.format(yesterday) == format.format(date)
    }
}