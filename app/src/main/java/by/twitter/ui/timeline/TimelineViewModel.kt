package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.main.MainFragment
import javax.inject.Inject

class TimelineViewModel @Inject constructor(private val tweetRepository: TweetRepository) : ViewModel() {

    private val tweetsData: LiveData<List<TweetWithUser>> by lazy {
        tweetRepository.homeTimeline()
    }
    var position: Int = 0

    lateinit var mainFragment: MainFragment


    fun getTweets(): LiveData<List<TweetWithUser>> {
        return tweetsData
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
        tweetRepository.deleteTweet(tweet)
    }

}