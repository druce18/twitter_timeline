package by.twitter.network

import by.twitter.util.TwitterAuthUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TwitterConnection {

    private val base_url = "https://api.twitter.com/1.1/"

    private val client by lazy {
        OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val r = chain.request()
                        val header = TwitterAuthUtil.generateAuthHeader(r.method, r.url)
                        val newRequest = r.newBuilder()
                                .addHeader("Authorization", header)
                                .build()
                        return chain.proceed(newRequest)
                    }
                })
                .build()
    }


    val twitterService: TwitterService by lazy {
        Retrofit.Builder()
                .client(client)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TwitterService::class.java)
    }


}