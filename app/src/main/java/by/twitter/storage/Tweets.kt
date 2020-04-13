package by.twitter.storage

import by.twitter.model.Tweet
import by.twitter.network.TwitterServiceImpl
import by.twitter.util.TwitterAuthUtil
import com.google.gson.Gson
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
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
        }
        update()
    }

    override fun read(): List<Tweet> {
        if (flag) {
            update()
            flag = false
        }
        return tweets
    }

    override fun update() {

        TwitterServiceImpl.twitterService
                .getTimelineHome()
                .enqueue(object : Callback<List<Tweet>> {
                    override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call<List<Tweet>>, response: retrofit2.Response<List<Tweet>>) {
                        val list = response.body()
                        if (list != null) {
                            tweets = list.toMutableList()
                        }
                        println("Call result: ${tweets.joinToString(separator = "\n")}")
                    }

                })
    }


    override fun delete(id: Long) {

    }

}