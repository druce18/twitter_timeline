package by.twitter.storage

import androidx.lifecycle.MutableLiveData
import by.twitter.network.model.TweetPayload

interface TweetRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun homeTimeline()

    fun getUserTimeline(userId: Long): MutableLiveData<List<TweetPayload>>

    fun retweet(id: Long)

    fun unretweet(id: Long)

    fun favoritesCreate(id: Long)

    fun favoritesDestroy(id: Long)

    fun delete(id: Long)

}