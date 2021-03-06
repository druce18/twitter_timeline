package by.twitter.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.Tweet
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
        tweetsData = appDatabase.tweetDao().getAllWithUser(tweetId)
    }

    fun likeOrDislikeTweet(tweet: Tweet) {
        if (tweet.favorited) {
            tweetRepository.favoritesDestroy(tweet.id)
        } else {
            tweetRepository.favoritesCreate(tweet.id)
        }
    }

    fun retweetOrUnretweet(tweet: Tweet) {
        if (tweet.retweeted) {
            tweetRepository.unretweet(tweet.id)
        } else {
            tweetRepository.retweet(tweet.id)
        }
        tweetRepository.deleteTweet(tweet)
    }

}