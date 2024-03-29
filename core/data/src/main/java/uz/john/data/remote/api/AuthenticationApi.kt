package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.john.data.remote.model.authentication.get.CreateSessionResponse
import uz.john.data.remote.model.authentication.get.RequestTokenResponse
import uz.john.data.remote.model.authentication.get.UserAccountDetailsData
import uz.john.data.remote.model.authentication.post.CreateSessionRequest
import uz.john.data.remote.model.authentication.post.ValidateTokenRequest

internal const val REQUEST_TOKEN_ENDPOINT = "authentication/token/new"
internal const val VALIDATE_REQUEST_TOKEN_ENDPOINT = "authentication/token/validate_with_login"
internal const val CREATE_SESSION_ENDPOINT = "authentication/session/new"
internal const val GET_ACCOUNT_DETAILS_ENDPOINT = "account/{account_id}"
internal const val ACCOUNT_ID_PATH = "account_id"
internal const val SESSION_ID_PATH = "session_id"

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

    @GET(GET_ACCOUNT_DETAILS_ENDPOINT)
    suspend fun getAccountDetails(
        @Path(ACCOUNT_ID_PATH) accountId: Int,
        @Query(SESSION_ID_PATH) sessionId: String
    ): Response<UserAccountDetailsData>
}