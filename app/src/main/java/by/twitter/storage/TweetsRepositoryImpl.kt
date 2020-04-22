package by.twitter.storage

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.twitter.model.Tweet
import by.twitter.network.TwitterServiceImpl
import retrofit2.Call
import retrofit2.Callback


object TweetsRepositoryImpl : TweetsRepository {

    private val _requestEnd = MutableLiveData(false)
    val requestEnd: LiveData<Boolean> = _requestEnd


    override fun create(text: String) {
        _requestEnd.value = false

        TwitterServiceImpl.twitterService
                .postUpdateTweet(text)
                .enqueue(object : Callback<Tweet> {
                    override fun onFailure(call: Call<Tweet>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<Tweet>, response: retrofit2.Response<Tweet>) {
                        val tweet = response.body()
                        println("New tweet: $tweet")

                        _requestEnd.value = true
                    }
                })

    }

    override fun update(): MutableLiveData<List<Tweet>> {
        val tweetsLiveData = MutableLiveData<List<Tweet>>()

        TwitterServiceImpl.twitterService
                .getTimelineHome()
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

    override fun delete(id: Long) {

    }

}