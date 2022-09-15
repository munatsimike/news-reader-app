package nl.project.newsreader2022.network

import com.skydoves.sandwich.ApiResponse
import nl.project.newsreader2022.model.MyData
import nl.project.newsreader2022.model.Token
import okhttp3.ResponseBody
import retrofit2.http.*

private var myToken: String = ""

fun updateHeaderToken(token: Token) {
    myToken = token.AuthToken
}

interface NewsApiService {
    @GET("Articles")
    suspend fun getInitArticlesAsync(): ApiResponse<MyData>

    @GET("Articles/liked")
    suspend fun likedArticlesAsync(@Header("x-authtoken") token: String? = myToken): ApiResponse<MyData>

    @GET("Articles/{id}")
    suspend fun getMoreArticlesAsync(
        @Path("id") nextId: Int,
        @Query("count") count: Int,
    ): ApiResponse<MyData>

    @PUT("Articles/{id}/like")
    suspend fun likeArticleAsync(
        @Path("id") id: Int,
        @Header("x-authtoken") token: String? = myToken,
    ): ApiResponse<ResponseBody>

    @DELETE("Articles/{id}/like")
    suspend fun disLikeArticleAsync(
        @Path("id") id: Int,
        @Header("x-authtoken") token: String? = myToken,
    ): ApiResponse<ResponseBody>
}



