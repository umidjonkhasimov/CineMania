package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.api.AuthenticationApi
import uz.john.data.remote.model.authentication.get.CreateSessionResponse
import uz.john.data.remote.model.authentication.get.RequestTokenResponse
import uz.john.data.remote.model.authentication.post.CreateSessionRequest
import uz.john.data.remote.model.authentication.post.ValidateTokenRequest
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun signIn(username: String, password: String): ResultModel<Unit> = withContext(Dispatchers.IO) {
        val result = createRequestToken()

        return@withContext when (result) {
            is ResultModel.Success -> {
                val resultToken = validateRequestToken(
                    ValidateTokenRequest(
                        requestToken = result.data.requestToken,
                        username = username,
                        password = password
                    )
                )
                when (resultToken) {
                    is ResultModel.Success -> {
                        val sessionResult =
                            createSession(
                                CreateSessionRequest(
                                    result.data.requestToken
                                )
                            )
                        when (sessionResult) {
                            is ResultModel.Success -> {
                                dataStoreRepository.saveSessionId(sessionResult.data.sessionId)
                                ResultModel.Success(Unit)
                            }

                            is ResultModel.Error -> {
                                ResultModel.Error(sessionResult.error)
                            }

                            is ResultModel.Exception -> {
                                ResultModel.Exception(sessionResult.throwable)
                            }
                        }
                    }

                    is ResultModel.Error -> {
                        ResultModel.Error(resultToken.error)
                    }

                    is ResultModel.Exception -> {
                        ResultModel.Exception(resultToken.throwable)
                    }
                }
            }

            is ResultModel.Error -> {
                ResultModel.Error(result.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(result.throwable)
            }
        }
    }

    private suspend fun createRequestToken(): ResultModel<RequestTokenResponse> = invokeRequest {
        authenticationApi.createRequestToken()
    }

    private suspend fun validateRequestToken(validateTokenRequest: ValidateTokenRequest): ResultModel<Unit> = invokeRequest {
        authenticationApi.validateRequestToken(validateTokenRequest)
    }

    private suspend fun createSession(createSessionRequest: CreateSessionRequest): ResultModel<CreateSessionResponse> =
        invokeRequest {
            authenticationApi.createSession(createSessionRequest)
        }
}