package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.network.model.TweetPayload
import by.twitter.network.model.UserPayload
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.entity.Tweet
import javax.inject.Inject

class TimelineViewModel @Inject constructor(
        private val tweetRepository: TweetRepository,
        private val db: AppDatabase
) : ViewModel() {

    private lateinit var tweetsData: LiveData<List<Tweet>>
    var position = 0
    lateinit var user: UserPayload


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

    fun setTweetsForTimeline() {
//        tweetsData = tweetRepository.getHomeTimeline()
        tweetsData = db.tweetDao().getAll()
    }

    fun setTweetsForUser() {
//        tweetsData = tweetRepository.getUserTimeline(user.id)
    }

    fun likeOrDislikeTweet(tweet: TweetPayload, pos: Int) {
        position = pos
        val tweetLiveData = if (tweet.favorited) {
            tweetRepository.favoritesDestroy(tweet.id)
        } else {
            tweetRepository.favoritesCreate(tweet.id)
        }

//        val index = tweetsData.value?.indexOf(tweet)
//        val value = tweetsData.value?.toMutableList()
//        tweetLiveData.observeForever(Observer {
//            if (it != null) {
//                value?.removeAt(index!!)
//                value?.add(index!!, it)
//                tweetsData.value = value
//            }
//        })
    }

    fun retweetOrUnretweet(tweet: TweetPayload, pos: Int) {
        position = pos
        val tweetLiveData = if (tweet.retweeted) {
            tweetRepository.unretweet(tweet.id)
        } else {
            tweetRepository.retweet(tweet.id)
        }

//        val index = tweetsData.value?.indexOf(tweet)
//        val value = tweetsData.value?.toMutableList()
//        tweetLiveData.observeForever(Observer {
//            if (it != null) {
//                value?.removeAt(index!!)
//                value?.add(index!!, it)
//                tweetsData.value = value
//            }
//        })
    }


}