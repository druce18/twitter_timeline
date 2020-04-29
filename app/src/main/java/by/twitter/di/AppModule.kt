package by.twitter.di

import android.content.Context
import by.twitter.TwitterApplication
import by.twitter.network.TwitterService
import by.twitter.util.TwitterAuthUtil
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    private val baseUrl = "https://api.twitter.com/1.1/"

    @Singleton
    @Provides
    fun twitterConnection(): TwitterService {
        val client by lazy {
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

        val twitterConnection: TwitterService by lazy {
            Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TwitterService::class.java)
        }

        return twitterConnection
    }

    @Provides
    fun provideContext(application: TwitterApplication): Context = application.applicationContext

}