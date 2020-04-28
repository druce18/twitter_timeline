package by.twitter.storage

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import by.twitter.model.TweetPayload
import by.twitter.network.TwitterService
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TweetRepositoryImpl @Inject constructor(private val twitterService: TwitterService) : TweetRepository {

    override fun create(text: String): MutableLiveData<Boolean> {
        val requestEnd = MutableLiveData(false)
        twitterService
                .postUpdateTweet(text)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweet = response.body()
                        Timber.i("New tweet: $tweet")
                        requestEnd.value = true
                    }
                })
        return requestEnd
    }

    override fun getHomeTimeline(): MutableLiveData<List<TweetPayload>> {
        val tweetsLiveData = MutableLiveData<List<TweetPayload>>()
        twitterService
                .getHomeTimeline()
                .enqueue(object : Callback<List<TweetPayload>> {
                    override fun onFailure(call: Call<List<TweetPayload>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<TweetPayload>>, response: retrofit2.Response<List<TweetPayload>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            tweetsLiveData.value = tweetsList
                            Timber.i("Call result: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })
        return tweetsLiveData
    }

    override fun getUserTimeline(userId: Long): MutableLiveData<List<TweetPayload>> {
        val userTweets = MutableLiveData<List<TweetPayload>>()
        twitterService
                .getUserTimeline(userId = userId)
                .enqueue(object : Callback<List<TweetPayload>> {
                    override fun onFailure(call: Call<List<TweetPayload>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<TweetPayload>>, response: retrofit2.Response<List<TweetPayload>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            userTweets.value = tweetsList
                            Timber.i("Call result user tweets: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })
        return userTweets
    }

    override fun retweet(id: Long): MutableLiveData<TweetPayload> {
        val tweetLiveData = MutableLiveData<TweetPayload>()
        twitterService
                .postRetweet(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweet = response.body()
                        tweetLiveData.value = tweet
                        Timber.i("retweet: $tweet")
                    }
                })
        return tweetLiveData
    }

    override fun unretweet(id: Long): MutableLiveData<TweetPayload> {
        val tweetLiveData = MutableLiveData<TweetPayload>()
        twitterService
                .postUnretweet(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweet = response.body()
                        tweetLiveData.value = tweet
                        Timber.i("unretweet: $tweet")
                    }
                })
        return tweetLiveData
    }

    override fun favoritesCreate(id: Long): MutableLiveData<TweetPayload> {
        val tweetLiveData = MutableLiveData<TweetPayload>()
        twitterService
                .postFavoritesCreate(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweet = response.body()
                        tweetLiveData.value = tweet
                        Timber.i("like tweet: $tweet")
                    }
                })
        return tweetLiveData
    }

    override fun favoritesDestroy(id: Long): MutableLiveData<TweetPayload> {
        val tweetLiveData = MutableLiveData<TweetPayload>()
        twitterService
                .postFavoritesDestroy(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweet = response.body()
                        tweetLiveData.value = tweet
                        Timber.i("dislike tweet: $tweet")
                    }
                })
        return tweetLiveData
    }

    override fun delete(id: Long) {

    }

}