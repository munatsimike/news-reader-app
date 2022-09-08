package nl.project.newsreader2022.network

import nl.project.newsreader2022.model.MyData
import retrofit2.Call
import retrofit2.http.*

interface NewsApiService {
    @GET("Articles")
    fun getInitArticlesAsync(): Call<MyData>

    @GET("Articles/liked")
    fun likedArticlesAsync(): Call<MyData>

    @GET("Articles/{id}")
    fun getMoreArticlesAsync(
        @Path("id") nextId: Int,
        @Query("count") count: Int
    ): Call<MyData>

    @PUT("Articles/{id}/like")
    suspend fun likeArticleAsync(@Path("id") id: Int)

    @DELETE("Articles/{id}/like")
    suspend fun disLikeArticleAsync(@Path("id") id: Int)
}