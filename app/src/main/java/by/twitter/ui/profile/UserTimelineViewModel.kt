package by.twitter.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.TweetRepository
import javax.inject.Inject

class UserTimelineViewModel @Inject constructor(private val tweetRepository: TweetRepository) : ViewModel() {

    private var tweetsData = MutableLiveData<List<Tweet>>()


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

    fun updateTimeline(userId: Long) {
        tweetsData = tweetRepository.getUserTimeline(userId)
    }

}