package by.twitter.ui.createtweet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.storage.Tweets

class CreateTweetViewModel : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _goBackToTimeline = MutableLiveData(false)
    val goBackToTimeline: LiveData<Boolean> = _goBackToTimeline

    init {
        Tweets.processingEnd.observeForever(Observer {
            if(it){
                _loading.value = false
                _goBackToTimeline.value = it
            }
        })
    }

    fun createTweet(text: String) {
        _loading.value = true
        Tweets.create(text)
    }

}