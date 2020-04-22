package by.twitter.storage

import androidx.lifecycle.MutableLiveData
import by.twitter.model.Tweet

interface TweetsRepository {

    fun create(text: String): MutableLiveData<Boolean>

    fun update(): MutableLiveData<List<Tweet>>

    fun delete(id: Long)

}