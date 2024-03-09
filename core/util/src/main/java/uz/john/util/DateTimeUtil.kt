package uz.john.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
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
    val inputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
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