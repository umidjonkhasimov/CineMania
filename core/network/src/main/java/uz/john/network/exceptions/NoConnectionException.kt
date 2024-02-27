package uz.john.network.exceptions

import okio.IOException

class NoConnectionException(private val messageText: String) : IOException() {
    override val message: String
        get() = messageText
}