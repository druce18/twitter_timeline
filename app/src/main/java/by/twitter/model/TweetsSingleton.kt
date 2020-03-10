package by.twitter.model

import java.util.ArrayList

object TweetsSingleton {

    private val tweets = ArrayList<Tweet>()

    fun createTweet(username: String, massage: String) {
        val tweet = getTweet(username, massage)
        tweets.add(tweet)
    }

    fun readTweets() = tweets

    fun updateTweet(username: String, massage: String, index: Int) {
        if (index < tweets.size) {
            val tweet = getTweet(username, massage)
            tweets.removeAt(index)
            tweets.add(index, tweet)
        }
    }

    fun deleteTweet(index: Int) {
        if (index < tweets.size) {
            tweets.removeAt(index)
        }
    }

    private fun getTweet(username: String, massage: String): Tweet {
        val user = User(name = username)
        return Tweet(user = user, massage = massage)
    }

}