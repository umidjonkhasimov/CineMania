package uz.john.datastore.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import uz.john.datastore.UserPreferences
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val userData = dataStore.data.distinctUntilChanged()

    suspend fun setIsOnboarded(isOnboarded: Boolean) {
        dataStore.updateData {
            it.copy(isOnboarded = isOnboarded)
        }
    }

    suspend fun saveSessionId(sessionId: String) {
        dataStore.updateData {
            it.copy(sessionId = sessionId)
        }
    }
}