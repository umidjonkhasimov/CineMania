package uz.john.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import uz.john.data.local.datastore.UserPreferencesData
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferencesData>
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