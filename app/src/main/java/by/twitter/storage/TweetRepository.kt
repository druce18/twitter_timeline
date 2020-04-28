package by.twitter.storage

import androidx.lifecycle.MutableLiveData
import by.twitter.model.TweetPayload

interface TweetRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun getHomeTimeline(): MutableLiveData<List<TweetPayload>>

    fun getUserTimeline(userId: Long): MutableLiveData<List<TweetPayload>>

    fun retweet(id: Long): MutableLiveData<TweetPayload>

    fun unretweet(id: Long): MutableLiveData<TweetPayload>

    fun favoritesCreate(id: Long): MutableLiveData<TweetPayload>

    fun favoritesDestroy(id: Long): MutableLiveData<TweetPayload>

    fun delete(id: Long)

}