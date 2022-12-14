package nl.project.newsreader2022.network

import com.skydoves.sandwich.ApiResponse
import nl.project.newsreader2022.model.Token
import nl.project.newsreader2022.model.UserRegistrationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// rest API calls
interface UserService {
    @FormUrlEncoded
    @POST("Users/login")
    suspend fun userLoginAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<Token>

    @FormUrlEncoded
    @POST("Users/register")
    suspend fun registerUserAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<UserRegistrationResponse>
}