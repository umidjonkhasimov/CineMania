package uz.john.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val DOMAIN_DATE_PATTERN = "MMMM dd, yyyy"
private const val DATA_DATE_PATTERN = "yyyy-MM-dd"
private const val YEAR_ONLY_PATTERN = "yyyy"

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat(DATA_DATE_PATTERN, Locale.getDefault())
    val outputFormat = SimpleDateFormat(DOMAIN_DATE_PATTERN, Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        if (date != null)
            return outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        return this
    }
    return this
}

fun String.getYear(): String {
    val inputFormat = SimpleDateFormat(DOMAIN_DATE_PATTERN, Locale.getDefault())
    val outputFormat = SimpleDateFormat(YEAR_ONLY_PATTERN, Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        if (date != null)
            return outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        return this
    }
    return this
}

fun calculateDateXMonthsAgo(monthCount: Int): String {
    val dateFormat = SimpleDateFormat(DATA_DATE_PATTERN, Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -monthCount)
    return dateFormat.format(calendar.time)
}