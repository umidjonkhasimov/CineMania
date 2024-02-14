package uz.john.util

import android.util.Log

private const val TAG = "GGG"

fun logging(
    message: String,
    tag: String = TAG,
    title: String = "log"
) {
    Log.d(tag, "$title: $message")
}