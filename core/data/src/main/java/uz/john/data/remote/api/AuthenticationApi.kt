package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.john.data.remote.model.authentication.get.CreateSessionResponse
import uz.john.data.remote.model.authentication.get.RequestTokenResponse
import uz.john.data.remote.model.authentication.post.CreateSessionRequest
import uz.john.data.remote.model.authentication.post.ValidateTokenRequest

internal const val REQUEST_TOKEN_ENDPOINT = "authentication/token/new"
internal const val VALIDATE_REQUEST_TOKEN_ENDPOINT = "authentication/token/validate_with_login"
internal const val CREATE_SESSION_ENDPOINT = "authentication/session/new"

interface AuthenticationApi {
    @GET(REQUEST_TOKEN_ENDPOINT)
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST(VALIDATE_REQUEST_TOKEN_ENDPOINT)
    suspend fun validateRequestToken(
        @Body validateTokenRequest: ValidateTokenRequest
    ): Response<Unit>

    @POST(CREATE_SESSION_ENDPOINT)
    suspend fun createSession(
        @Body createSessionRequest: CreateSessionRequest
    ): Response<CreateSessionResponse>
}