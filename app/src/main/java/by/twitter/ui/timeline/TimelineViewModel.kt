package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.TweetWithUser
import javax.inject.Inject

class TimelineViewModel @Inject constructor(
        private val tweetRepository: TweetRepository,
        private val appDatabase: AppDatabase
) : ViewModel() {

    private lateinit var tweetsData: LiveData<List<TweetWithUser>>

    fun getTweets(): LiveData<List<TweetWithUser>> {
        return tweetsData
    }

    fun setTweetsTimeline() {
        tweetRepository.homeTimeline()
        tweetsData = appDatabase.tweetDao().getAllWithUser()
    }

}