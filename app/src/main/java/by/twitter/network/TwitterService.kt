package by.twitter.network

import by.twitter.network.model.TweetPayload
import retrofit2.Call
import retrofit2.http.*

interface TwitterService {

    @GET("statuses/home_timeline.json")
    fun getHomeTimeline(@Query("count") count: Long = 50): Call<List<TweetPayload>>

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(@Query("user_id") userId: Long,
                        @Query("count") count: Long = 50
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

    @GET("statuses/show.json?tweet_mode=extended")
    fun getAnswersByTweetId(
            @Query("id") id: Long,
            @Query("include_entities") includeEntities: Boolean = true
//            @Query("trim_user") trim_user: Boolean = false,
//            @Query("include_my_retweet") include_my_retweet: Boolean = false,
//            @Query("include_card_uri") include_card_uri: Boolean = false
    ): Call<TweetPayload>

}