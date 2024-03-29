package uz.john.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import uz.john.data.local.datastore.LanguageData
import uz.john.data.local.datastore.UiModeData
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

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.updateData {
            it.copy(isLoggedIn = isLoggedIn)
        }
    }

    suspend fun saveSessionId(sessionId: String) {
        dataStore.updateData {
            it.copy(sessionId = sessionId)
        }
    }

    suspend fun setUserLanguage(languageData: LanguageData) {
        dataStore.updateData {
            it.copy(languageData = languageData)
        }
    }

    suspend fun setIncludeAdult(includeAdult: Boolean) {
        dataStore.updateData {
            it.copy(includeAdult = includeAdult)
        }
    }

    suspend fun setUiMode(uiModeData: UiModeData) {
        dataStore.updateData {
            it.copy(uiMode = uiModeData)
        }
    }
}