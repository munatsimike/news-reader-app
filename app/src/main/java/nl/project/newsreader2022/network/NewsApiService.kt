package nl.project.newsreader2022.network

import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.NetworkResponse
import retrofit2.http.*

interface NewsApiService {
    @GET("Articles")
    fun getInitArticlesAsync(): Deferred<NetworkResponse>

    @GET("Articles/{id}")
    fun getMoreArticlesAsync(
        @Path("id") nextId: Int,
        @Query("count") count: Int
    ): Deferred<NetworkResponse>

    @PUT("Articles/{id}/like")
    fun likeArticleAsync(@Path("id") id: Int): Deferred<Void>

    @DELETE("Articles/{id}/like")
    fun disLikeArticleAsync(@Path("id") id: Int): Deferred<Void>
}