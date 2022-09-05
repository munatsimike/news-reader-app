package nl.project.newsreader2022.network

import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
     @GET("Articles")
     fun getInitArticlesAsync() : Deferred<NetworkResponse>

     @GET("Articles/{id}")
     fun getMoreArticlesAsync(@Path("id") nextId: Int, @Query("count") count: Int): Deferred<NetworkResponse>
}