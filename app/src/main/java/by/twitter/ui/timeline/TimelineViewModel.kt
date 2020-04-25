package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.TweetRepository
import javax.inject.Inject

class TimelineViewModel @Inject constructor(private val tweetRepository: TweetRepository) : ViewModel() {

    private val tweetsData: MutableLiveData<List<Tweet>> by lazy {
        tweetRepository.update()
    }


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

}