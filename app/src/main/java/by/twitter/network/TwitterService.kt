package by.twitter.network

import by.twitter.network.model.TweetPayload
import retrofit2.Call
import retrofit2.http.*

interface TwitterService {

    @GET("statuses/home_timeline.json")
    fun getHomeTimeline(@Query("count") count: Long = 55): Call<List<TweetPayload>>

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(@Query("user_id") userId: Long,
                        @Query("count") count: Long = 55
    ): Call<List<TweetPayload>>

    @POST("statuses/update.json")
    fun postUpdateTweet(@Query("status") text: String): Call<TweetPayload>

    @POST("statuses/retweet/{id}.json")
    fun postRetweet(@Path("id") id: Long): Call<TweetPayload>

    @POST("statuses/unretweet/{id}.json")
    fun postUnretweet(@Path("id") id: Long): Call<TweetPayload>

    @POST("favorites/create.json")
    fun postFavoritesCreate(@Query("id") id: Long): Call<TweetPayload>

    @POST("favorites/destroy.json")
    fun postFavoritesDestroy(@Query("id") id: Long): Call<TweetPayload>

    @GET("statuses/show.json")
    fun getAnswersByTweetId(
            @Query("id") id: Long,
            @Query("include_entities") includeEntities: Boolean = true
    ): Call<TweetPayload>

}