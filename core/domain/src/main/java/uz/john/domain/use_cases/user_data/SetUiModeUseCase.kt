package uz.john.domain.use_cases.user_data

import uz.john.data.repository.DataStoreRepository
import uz.john.domain.model.UiMode
import uz.john.domain.model.toData
import javax.inject.Inject

class SetUiModeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(uiMode: UiMode) {
        dataStoreRepository.setUiMode(uiMode.toData())
    }
}