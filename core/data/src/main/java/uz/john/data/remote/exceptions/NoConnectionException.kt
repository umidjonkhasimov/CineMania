package uz.john.data.remote.exceptions

import okio.IOException

class NoConnectionException(private val messageText: String) : IOException() {
    override val message: String
        get() = messageText
}