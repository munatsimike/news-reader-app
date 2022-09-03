package nl.project.newsreader2022.network

import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.AuthToken
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// rest API calls
interface UserService {
    @FormUrlEncoded
    @POST("Users/login")
    fun userLoginAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): Deferred<AuthToken>

    @FormUrlEncoded
    @POST("Users/register")
    fun registerUserAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): Deferred<ResponseBody>
}