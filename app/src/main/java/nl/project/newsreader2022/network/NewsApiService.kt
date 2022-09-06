package nl.project.newsreader2022.network

import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.NetworkResponse
import retrofit2.Call
import retrofit2.http.*

interface NewsApiService {
    @GET("Articles")
    fun getInitArticlesAsync(): Deferred<NetworkResponse>

    @GET("Articles/liked")
    fun likedArticlesAsync(): Deferred<NetworkResponse>

    @GET("Articles/{id}")
    fun getMoreArticlesAsync(
        @Path("id") nextId: Int,
        @Query("count") count: Int
    ): Deferred<NetworkResponse>

    @PUT("Articles/{id}/like")
   suspend fun likeArticleAsync(@Path("id") id: Int)

    @DELETE("Articles/{id}/like")
   suspend fun disLikeArticleAsync(@Path("id") id: Int)
}