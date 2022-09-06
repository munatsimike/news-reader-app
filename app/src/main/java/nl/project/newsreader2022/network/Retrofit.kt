package nl.project.newsreader2022.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import nl.project.newsreader2022.BuildConfig
import nl.project.newsreader2022.model.AuthToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://inhollandbackend.azurewebsites.net/api/"

private val moshi = Moshi.Builder() // adapter
    .add(KotlinJsonAdapterFactory())
    .build()

private var authToken: AuthToken? = AuthToken("16418-271b9ca8-f44e-4201-a7ca-1cd988b00e9f")

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder().addInterceptor {  chain ->
            chain.proceed(chain.request().newBuilder().also{
                authToken?.let { it1 -> it.addHeader("x-authtoken", it1.AuthToken) }
            }.build())
        }.also { client ->
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }
        }.build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}

object UserApi {
    val retrofitService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}


