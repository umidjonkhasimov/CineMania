package uz.john.util

fun Long.formatWithCommaSeparators(): String {
    return String.format("%,d", this)
}