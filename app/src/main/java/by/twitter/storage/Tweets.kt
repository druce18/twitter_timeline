package by.twitter.storage

import by.twitter.model.Tweet
import by.twitter.model.User
import java.util.ArrayList

object Tweets :
    Crud<Tweet> {

    private val tweets: ArrayList<Tweet> = ArrayList<Tweet>()

    override fun create(element: Tweet) {
        tweets.add(element)
    }

    override fun read() = tweets.toList()

    override fun update(element: Tweet, index: Int) {
        if (index < tweets.size) {
            tweets.removeAt(index)
            tweets.add(index, element)
        }
    }

    override fun delete(index: Int) {
        if (index < tweets.size) {
            tweets.removeAt(index)
        }
    }

    fun newTweet(username: String, massage: String): Tweet {
        val user = User(name = username)
        return Tweet(user = user, massage = massage)
    }

}