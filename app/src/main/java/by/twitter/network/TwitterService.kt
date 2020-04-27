package by.twitter.network

import by.twitter.model.Tweet
import retrofit2.Call
import retrofit2.http.*

interface TwitterService {

    @GET("statuses/home_timeline.json")
    fun getHomeTimeline(@Query("count") count: Long = 50): Call<List<Tweet>>

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(@Query("user_id") userId: Long,
                        @Query("count") count: Long = 50
    ): Call<List<Tweet>>

    @POST("statuses/update.json")
    fun postUpdateTweet(@Query("status") text: String): Call<Tweet>

    @POST("statuses/retweet/{id}.json")
    fun postRetweet(@Path("id") id: Long): Call<Tweet>

    @POST("statuses/unretweet/{id}.json")
    fun postUnretweet(@Path("id") id: Long): Call<Tweet>

    @POST("favorites/create.json")
    fun postFavoritesCreate(@Query("id") id: Long): Call<Tweet>

    @POST("favorites/destroy.json")
    fun postFavoritesDestroy(@Query("id") id: Long): Call<Tweet>

}