package uz.john.domain.use_cases.user_data

import uz.john.data.repository.DataStoreRepository
import uz.john.domain.model.Language
import uz.john.domain.model.toData
import javax.inject.Inject

class SetUserLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(language: Language) {
        dataStoreRepository.setUserLanguage(language.toData())
    }
}