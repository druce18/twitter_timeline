package by.twitter.storage

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import by.twitter.model.Tweet
import by.twitter.network.TwitterService
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TweetRepositoryImpl @Inject constructor(private val twitterService: TwitterService) : TweetRepository {

    override fun create(text: String): MutableLiveData<Boolean> {
        val requestEnd = MutableLiveData(false)
        twitterService
                .postUpdateTweet(text)
                .enqueue(object : Callback<Tweet> {
                    override fun onFailure(call: Call<Tweet>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<Tweet>, response: retrofit2.Response<Tweet>) {
                        val tweet = response.body()
                        println("New tweet: $tweet")

                        requestEnd.value = true
                    }
                })

        return requestEnd
    }

    override fun getHomeTimeline(): MutableLiveData<List<Tweet>> {
        val tweetsLiveData = MutableLiveData<List<Tweet>>()
        twitterService
                .getHomeTimeline()
                .enqueue(object : Callback<List<Tweet>> {
                    override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<Tweet>>, response: retrofit2.Response<List<Tweet>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            tweetsLiveData.value = tweetsList
                            println("Call result: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })

        return tweetsLiveData
    }

    override fun getUserTimeline(userId: Long): MutableLiveData<List<Tweet>> {
        val userTweets = MutableLiveData<List<Tweet>>()
        twitterService
                .getUserTimeline(userId = userId)
                .enqueue(object : Callback<List<Tweet>> {
                    override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<Tweet>>, response: retrofit2.Response<List<Tweet>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            userTweets.value = tweetsList
                            println("Call result user tweets: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })

        return userTweets
    }

    override fun delete(id: Long) {

    }

}