package by.twitter.storage

import androidx.lifecycle.MutableLiveData
import by.twitter.model.Tweet

interface TweetRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun getHomeTimeline(): MutableLiveData<List<Tweet>>

    fun getUserTimeline(userId: Long): MutableLiveData<List<Tweet>>

    fun delete(id: Long)

}