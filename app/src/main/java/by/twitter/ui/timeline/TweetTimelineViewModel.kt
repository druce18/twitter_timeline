package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.network.model.UserPayload
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import javax.inject.Inject

class TweetTimelineViewModel @Inject constructor(
        private val tweetRepository: TweetRepository,
        private val appDatabase: AppDatabase
) : ViewModel() {

    private lateinit var tweetsData: LiveData<List<TweetWithUser>>
    var position = 0
    lateinit var user: UserPayload


    fun getTweets(): LiveData<List<TweetWithUser>> {
        return tweetsData
    }

    fun setTweetsTimeline() {
        tweetRepository.homeTimeline()
        tweetsData = appDatabase.tweetDao().getAllWithUser()
    }

    fun updateTweetsForTimeline() {
        tweetRepository.homeTimeline()
    }

    fun likeOrDislikeTweet(tweet: Tweet, pos: Int) {
        position = pos
        if (tweet.favorited) {
            tweetRepository.favoritesDestroy(tweet.id)
        } else {
            tweetRepository.favoritesCreate(tweet.id)
        }
    }

    fun retweetOrUnretweet(tweet: Tweet, pos: Int) {
        position = pos
        if (tweet.retweeted) {
            tweetRepository.unretweet(tweet.id)
        } else {
            tweetRepository.retweet(tweet.id)
        }
    }

}