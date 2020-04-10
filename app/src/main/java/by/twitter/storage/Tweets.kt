package by.twitter.storage

import by.twitter.model.Tweet
import by.twitter.util.TwitterAuthUtil
import com.google.gson.Gson
import okhttp3.*
import java.util.*
import java.util.concurrent.Executors

object Tweets : Crud<Tweet> {

    private var tweets = mutableListOf<Tweet>()
    private val executorService = Executors.newFixedThreadPool(1)
    private var flag = true

    override fun create(text: String) {
        executorService.submit {
            val parser = Gson()
            val url = "https://api.twitter.com/1.1/statuses/update.json"
            val client = OkHttpClient.Builder()
                    .addInterceptor(object : Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val r = chain.request()
                            val postParams = TreeMap<String, String>()
                            postParams["status"] = text
                            val header = TwitterAuthUtil.generateAuthHeader(r.method, r.url, postParams)
                            val newRequest = r.newBuilder()
                                    .addHeader("Authorization", header)
                                    .build()
                            return chain.proceed(newRequest)
                        }
                    })
                    .build()

            val formBody = FormBody.Builder()
                    .add("status", text)
                    .build()


            val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string()

            val tweet = parser.fromJson(json, Tweet::class.java)
            println("Call result: $tweet")
        }
        updateAll()
    }

    override fun read(): List<Tweet> {
        if (flag) {
            updateAll()
            flag = false
        }
        return tweets
    }

    override fun updateAll() {
        executorService.submit {
            val parser = Gson()
            val url = "https://api.twitter.com/1.1/statuses/home_timeline.json"
            val client = OkHttpClient.Builder()
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

            val request = Request.Builder()
                    .url(url)
                    .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string()

            tweets = parser.fromJson(json, Array<Tweet>::class.java).toMutableList()

            println("Call result: ${tweets.joinToString(separator = "\n")}")
        }
    }

    override fun delete(id: Long) {

    }

}