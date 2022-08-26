package nl.project.newsreader2022.network

import kotlinx.coroutines.Deferred
import nl.project.newsreader2022.model.NetworkResponse
import retrofit2.http.GET

interface NewsApiService {
     @GET("api/Articles")
     fun getNewsAsync() : Deferred<NetworkResponse>
}