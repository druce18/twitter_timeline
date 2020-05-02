package by.twitter.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.network.model.UserPayload
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
    var position = 0


    fun getTweets(): LiveData<List<TweetWithUser>> {
        return tweetsData
    }

    fun getTweetMain(): LiveData<TweetWithUser> {
        return appDatabase.tweetDao().getTweetWithUserByTweetId(tweetId)
    }

    fun setTweetsTimeline() {
//        tweetRepository.homeTimeline()
//        tweetsData = appDatabase.tweetDao().getAllWithUser()
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