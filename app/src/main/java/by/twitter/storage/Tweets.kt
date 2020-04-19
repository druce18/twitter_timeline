package by.twitter.storage

import android.accounts.NetworkErrorException
import by.twitter.model.Tweet
import by.twitter.network.TwitterServiceImpl
import retrofit2.Call
import retrofit2.Callback


object Tweets : Crud<Tweet> {

    private var tweets = mutableListOf<Tweet>()
    private var flagReadNetwork = true

    override fun create(text: String) {
        TwitterServiceImpl.twitterService
                .postUpdateTweet(text)
                .enqueue(object : Callback<Tweet> {
                    override fun onFailure(call: Call<Tweet>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<Tweet>, response: retrofit2.Response<Tweet>) {
                        val tweet = response.body()
                        if (tweet != null) {
                            tweets.add(0, tweet)
                        }
                        println("New tweet: $tweet")
                    }
                })
    }

    override fun read(): List<Tweet> {
        if (flagReadNetwork) {
            update()
            flagReadNetwork = false
        }
        return tweets
    }

    override fun update() {
        TwitterServiceImpl.twitterService
                .getTimelineHome()
                .enqueue(object : Callback<List<Tweet>> {
                    override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<Tweet>>, response: retrofit2.Response<List<Tweet>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            tweets = tweetsList.toMutableList()
                        }
                        println("Call result: ${tweets.joinToString(separator = "\n")}")
                    }
                })
    }


    override fun delete(id: Long) {

    }

}