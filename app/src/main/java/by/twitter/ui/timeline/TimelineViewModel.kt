package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.TweetsRepositoryImpl

class TimelineViewModel : ViewModel() {

    private val tweetsData: MutableLiveData<List<Tweet>> by lazy {
        TweetsRepositoryImpl.update()
    }


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

}