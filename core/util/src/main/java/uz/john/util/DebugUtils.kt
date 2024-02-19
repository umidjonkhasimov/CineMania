package uz.john.util

import android.util.Log

private const val TAG = "GGG"

fun <T> logging(
    message: T,
    tag: String = TAG,
) {
    Log.d(tag, message.toString())
}