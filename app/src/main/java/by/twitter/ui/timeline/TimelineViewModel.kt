package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.model.TweetPayload
import by.twitter.model.UserPayload
import by.twitter.storage.TweetRepository
import javax.inject.Inject

class TimelineViewModel @Inject constructor(private val tweetRepository: TweetRepository) : ViewModel() {

    private var tweetsData = MutableLiveData<List<TweetPayload>>()
    var position = 0
    lateinit var user: UserPayload


    fun getTweets(): LiveData<List<TweetPayload>> {
        return tweetsData
    }

    fun setTweetsForTimeline() {
        tweetsData = tweetRepository.getHomeTimeline()
    }

    fun setTweetsForUser() {
        tweetsData = tweetRepository.getUserTimeline(user.id)
    }

    fun likeOrDislikeTweet(tweet: TweetPayload, pos: Int) {
        position = pos
        val tweetLiveData = if (tweet.favorited) {
            tweetRepository.favoritesDestroy(tweet.id)
        } else {
            tweetRepository.favoritesCreate(tweet.id)
        }

        val index = tweetsData.value?.indexOf(tweet)
        val value = tweetsData.value?.toMutableList()
        tweetLiveData.observeForever(Observer {
            if (it != null) {
                value?.removeAt(index!!)
                value?.add(index!!, it)
                tweetsData.value = value
            }
        })
    }

    fun retweetOrUnretweet(tweet: TweetPayload, pos: Int) {
        position = pos
        val tweetLiveData = if (tweet.retweeted) {
            tweetRepository.unretweet(tweet.id)
        } else {
            tweetRepository.retweet(tweet.id)
        }

        val index = tweetsData.value?.indexOf(tweet)
        val value = tweetsData.value?.toMutableList()
        tweetLiveData.observeForever(Observer {
            if (it != null) {
                value?.removeAt(index!!)
                value?.add(index!!, it)
                tweetsData.value = value
            }
        })
    }


}