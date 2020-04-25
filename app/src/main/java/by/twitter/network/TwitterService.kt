package by.twitter.network

import by.twitter.model.Tweet
import retrofit2.Call
import retrofit2.http.*

interface TwitterService {

    @GET("statuses/home_timeline.json")
    fun getHomeTimeline(@Query("count") count: Long = 200): Call<List<Tweet>>

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(@Query("user_id") userId: Long,
                        @Query("count") count: Long = 100
    ): Call<List<Tweet>>

    @POST("statuses/update.json")
    fun postUpdateTweet(@Query("status") text: String): Call<Tweet>


}