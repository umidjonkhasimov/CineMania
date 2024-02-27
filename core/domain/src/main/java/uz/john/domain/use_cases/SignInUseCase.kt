package uz.john.domain.use_cases

import uz.john.data.repository.AuthRepository
import uz.john.util.ResultModel
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): ResultModel<Unit> {
        return authRepository.signIn(
            username = username,
            password = password
        )
    }
}