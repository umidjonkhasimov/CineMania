package uz.john.domain.use_cases.user_data

import uz.john.data.repository.DataStoreRepository
import javax.inject.Inject


class SetUserOnboardedUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isOnboarded: Boolean) {
        dataStoreRepository.setIsOnboarded(isOnboarded)
    }
}