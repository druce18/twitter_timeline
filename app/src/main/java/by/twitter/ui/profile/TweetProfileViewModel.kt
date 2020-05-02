package by.twitter.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.TweetWithUser
import javax.inject.Inject

class TweetProfileViewModel @Inject constructor(
        private val tweetRepository: TweetRepository,
        private val appDatabase: AppDatabase
) : ViewModel() {

    private lateinit var tweetsData: LiveData<List<TweetWithUser>>
    var tweetId: Long = 0L


    fun getTweets(): LiveData<List<TweetWithUser>> {
        return tweetsData
    }

    fun mainTweet(): LiveData<TweetWithUser> {
        return appDatabase.tweetDao().getTweetWithUserByTweetId(tweetId)
    }

    fun setTweetsTimeline() {
        tweetRepository.getAnswersByTweetId(tweetId)
        tweetsData = appDatabase.tweetDao().getAllWithUser(tweetId)
    }

}