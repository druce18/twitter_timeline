package by.twitter.network

import by.twitter.model.Tweet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface TwitterService {

    @GET("statuses/home_timeline.json")
    fun getTimelineHome(): Call<List<Tweet>>

    @POST("statuses/update.json")
    fun postUpdateTweet()

}