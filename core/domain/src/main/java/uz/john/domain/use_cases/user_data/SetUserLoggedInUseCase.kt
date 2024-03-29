package uz.john.domain.use_cases.user_data

import uz.john.data.repository.DataStoreRepository
import javax.inject.Inject


class SetUserLoggedInUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isLoggedIn: Boolean) {
        dataStoreRepository.setIsLoggedIn(isLoggedIn)
    }
}