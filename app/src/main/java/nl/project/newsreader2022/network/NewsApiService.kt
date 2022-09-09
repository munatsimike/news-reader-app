package nl.project.newsreader2022.network

import com.skydoves.sandwich.ApiResponse
import nl.project.newsreader2022.model.MyData
import okhttp3.ResponseBody
import retrofit2.http.*

interface NewsApiService {
    @GET("Articles")
    suspend fun getInitArticlesAsync(): ApiResponse<MyData>

    @GET("Articles/liked")
    suspend fun likedArticlesAsync(): ApiResponse<MyData>

    @GET("Articles/{id}")
    suspend fun getMoreArticlesAsync(
        @Path("id") nextId: Int,
        @Query("count") count: Int
    ): ApiResponse<MyData>

    @PUT("Articles/{id}/like")
    suspend fun likeArticleAsync(@Path("id") id: Int): ApiResponse<ResponseBody>

    @DELETE("Articles/{id}/like")
    suspend fun disLikeArticleAsync(@Path("id") id: Int): ApiResponse<ResponseBody>
}