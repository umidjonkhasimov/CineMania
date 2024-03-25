package uz.john.data.local.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferencesData> {
    override val defaultValue: UserPreferencesData
        get() = UserPreferencesData()

    override suspend fun readFrom(input: InputStream): UserPreferencesData {
        return try {
            Json.decodeFromString(
                deserializer = UserPreferencesData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            exception.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPreferencesData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserPreferencesData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}