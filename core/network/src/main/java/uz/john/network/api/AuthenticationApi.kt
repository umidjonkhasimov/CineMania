package uz.john.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.john.network.CREATE_SESSION_ENDPOINT
import uz.john.network.REQUEST_TOKEN_ENDPOINT
import uz.john.network.VALIDATE_REQUEST_TOKEN_ENDPOINT
import uz.john.network.model.authentication.get.CreateSessionResponse
import uz.john.network.model.authentication.get.RequestTokenResponse
import uz.john.network.model.authentication.post.CreateSessionRequest
import uz.john.network.model.authentication.post.ValidateTokenRequest

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