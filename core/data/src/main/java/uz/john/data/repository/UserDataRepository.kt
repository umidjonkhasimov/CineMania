package uz.john.data.repository

import kotlinx.coroutines.flow.map
import uz.john.data.model.UserData
import uz.john.datastore.repository.DataStoreRepository
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun setIsOnboarded(isOnboarded: Boolean) {
        dataStoreRepository.setIsOnboarded(isOnboarded)
    }

    val userData = dataStoreRepository.userData.map {
        UserData(
            isOnboarded = it.isOnboarded,
            isLoggedIn = it.isLoggedIn,
            sessionId = it.sessionId
        )
    }
}