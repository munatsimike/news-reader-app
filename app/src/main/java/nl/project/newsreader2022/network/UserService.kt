package nl.project.newsreader2022.network

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.AuthToken
import nl.project.newsreader2022.model.UserRegistrationResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call


// rest API calls
interface UserService {
    @FormUrlEncoded
    @POST("Users/login")
    suspend fun userLoginAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<AuthToken>

    @FormUrlEncoded
    @POST("Users/register")
    suspend fun registerUserAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<UserRegistrationResponse>
}