package uz.john.domain.use_cases.user_data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.repository.DataStoreRepository
import uz.john.domain.model.UserPreferences
import uz.john.domain.model.toDomain
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<UserPreferences> {
        return dataStoreRepository.userData.map { userPrefs ->
            userPrefs.toDomain()
        }
    }
}