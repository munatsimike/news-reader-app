package nl.project.newsreader2022.network

import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import nl.project.newsreader2022.BuildConfig
import nl.project.newsreader2022.model.AuthToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://inhollandbackend.azurewebsites.net/api/"

private var authToken: AuthToken? = AuthToken("16418-8da61777-513f-412e-ab2a-1bc51a7ffe0e")

private val moshi = Moshi.Builder() // adapter
    .add(KotlinJsonAdapterFactory())
    .build()

private var logger: OkHttpClient =
    OkHttpClient.Builder()
        .also { client ->
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }
        }.build()

private var okHttpClientHeader: OkHttpClient =
    OkHttpClient.Builder().addInterceptor { chain ->
        chain.proceed(chain.request().newBuilder().also {
            authToken?.let { it1 -> it.addHeader("x-authtoken", it1.AuthToken) }
        }.build())
    }.build()

private val retrofit by lazy {
    Retrofit.Builder()
        .client(okHttpClientHeader)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

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