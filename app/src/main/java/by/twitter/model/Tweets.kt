package by.twitter.model

import java.util.ArrayList

object Tweets : CRUD<Tweet> {

    private val tweets: ArrayList<Tweet> = ArrayList<Tweet>()

    override fun createTweet(element: Tweet) {
        tweets.add(element)
    }

    override fun readTweets() = ArrayList(tweets)

    override fun updateTweet(element: Tweet, index: Int) {
        if (index < tweets.size) {
            tweets.removeAt(index)
            tweets.add(index, element)
        }
    }

    override fun deleteTweet(index: Int) {
        if (index < tweets.size) {
            tweets.removeAt(index)
        }
    }

    fun newTweet(username: String, massage: String): Tweet {
        val user = User(name = username)
        return Tweet(user = user, massage = massage)
    }

}