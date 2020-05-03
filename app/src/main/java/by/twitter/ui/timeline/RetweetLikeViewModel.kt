package by.twitter.ui.timeline

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.Tweet
import javax.inject.Inject

class RetweetLikeViewModel @Inject constructor(
        private val tweetRepository: TweetRepository,
        private val appDatabase: AppDatabase
) : ViewModel() {

    var position = 0

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
        AsyncTask.execute {
            appDatabase.tweetDao().deleteTweet(tweet)
        }
    }

}