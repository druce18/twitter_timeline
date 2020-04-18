package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.Tweets

class TimelineViewModel : ViewModel() {

    private val tweetsData: MutableLiveData<List<Tweet>> by lazy {
        MutableLiveData<List<Tweet>>()
    }

    fun getTweets(): LiveData<List<Tweet>> {
        updateTweets()
        return tweetsData
    }

    private fun updateTweets() {
        Tweets.update()
        Tweets.requestEnd.observeForever(Observer {
            if (it) {
                tweetsData.value = Tweets.read()
            }
        })
    }
}