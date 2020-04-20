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

    private val observerRequest = Observer<Boolean> { t ->
        if (t!!) {
            _loading.value = false
            _goBackToTimeline.value = true
        } else {
            _loading.value = true
            _goBackToTimeline.value = false
        }
    }


    fun createTweet(text: String) {
        Tweets.create(text)
        Tweets.requestEnd.observeForever(observerRequest)
    }

    override fun onCleared() {
        super.onCleared()
        Tweets.requestEnd.removeObserver(observerRequest)
    }

}