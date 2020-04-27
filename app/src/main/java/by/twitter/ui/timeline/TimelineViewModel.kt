package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.model.User
import by.twitter.storage.TweetRepository
import javax.inject.Inject

class TimelineViewModel @Inject constructor(private val tweetRepository: TweetRepository) : ViewModel() {

    private var tweetsData = MutableLiveData<List<Tweet>>()

    lateinit var user: User


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

    fun setTweetsForTimeline() {
        tweetsData = tweetRepository.getHomeTimeline()
    }

    fun setTweetsForUser() {
        tweetsData = tweetRepository.getUserTimeline(user.id)
    }

    fun likeOrDislikeTweet(tweet: Tweet) {
        val tweetLiveData = if (tweet.favorited) {
            tweetRepository.favoritesDestroy(tweet.id)
        } else {
            tweetRepository.favoritesCreate(tweet.id)
        }

        val index = tweetsData.value?.indexOf(tweet)
        val value = tweetsData.value?.toMutableList()
        tweetLiveData.observeForever(Observer {
            if (it != null) {
                if (value != null && index != null) {
                    value.removeAt(index)
                    value.add(index, it)
                    tweetsData.value = value
                }
            }
        })
    }

    fun retweetOrUnretweet(tweet: Tweet) {
        val tweetLiveData = if (tweet.retweeted) {
            tweetRepository.unretweet(tweet.id)
        } else {
            tweetRepository.retweet(tweet.id)
        }

        val index = tweetsData.value?.indexOf(tweet)
        val value = tweetsData.value?.toMutableList()
        tweetLiveData.observeForever(Observer {
            if (it != null) {
                if (value != null && index != null) {
                    value.removeAt(index)
                    value.add(index, it)
                    tweetsData.value = value
                }
            }
        })
    }


}