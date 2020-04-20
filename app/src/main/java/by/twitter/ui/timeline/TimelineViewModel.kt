package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.Tweets

class TimelineViewModel : ViewModel() {

    private val tweetsData: MutableLiveData<List<Tweet>> by lazy {
        updateTweets()
        MutableLiveData<List<Tweet>>()
    }

    private val observerRequest = Observer<Boolean> { t ->
        if (t!!) {
            tweetsData.value = Tweets.read()
        }
    }


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

    override fun onCleared() {
        super.onCleared()
        Tweets.requestEnd.removeObserver(observerRequest)
    }

    private fun updateTweets() {
        Tweets.update()
        Tweets.requestEnd.observeForever(observerRequest)
    }

}