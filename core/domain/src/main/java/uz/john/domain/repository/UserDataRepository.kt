package uz.john.domain.repository

import kotlinx.coroutines.flow.map
import uz.john.domain.model.UserData
import uz.john.datastore.repository.DataStoreRepository
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    val userData = dataStoreRepository.userData.map {
        UserData(
            isOnboarded = it.isOnboarded,
            isLoggedIn = it.isLoggedIn,
            sessionId = it.sessionId
        )
    }

    suspend fun setIsOnboarded(isOnboarded: Boolean) {
        dataStoreRepository.setIsOnboarded(isOnboarded)
    }
}