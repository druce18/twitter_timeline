package by.twitter.storage

import androidx.lifecycle.MutableLiveData

interface TweetRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun homeTimeline()

    fun userTimeline(userId: Long)

    fun retweet(id: Long)

    fun unretweet(id: Long)

    fun favoritesCreate(id: Long)

    fun favoritesDestroy(id: Long)

    fun getAnswersByTweetId(id: Long)

    fun delete(id: Long)

}