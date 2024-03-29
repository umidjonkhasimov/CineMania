package uz.john.domain.use_cases.user_data

import uz.john.data.repository.AuthRepository
import uz.john.domain.model.profile.UserAccountDetails
import uz.john.domain.model.profile.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetUserAccountDetailsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        accountId: Int = 1,
        sessionId: String = ""
    ): ResultModel<UserAccountDetails> {
        val response = authRepository.getAccountDetails(
            accountId = accountId,
            sessionId = sessionId
        )
        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                ResultModel.Success(response.data.toDomain())
            }
        }
    }
}