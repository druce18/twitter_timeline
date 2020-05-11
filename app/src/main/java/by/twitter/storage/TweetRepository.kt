package by.twitter.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser

interface TweetRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun homeTimeline(): LiveData<List<TweetWithUser>>

    fun userTimeline(userId: Long): LiveData<List<TweetWithUser>>

    fun retweet(id: Long)

    fun unretweet(id: Long)

    fun favoritesCreate(id: Long)

    fun favoritesDestroy(id: Long)

    fun deleteTweet(tweet: Tweet)

}