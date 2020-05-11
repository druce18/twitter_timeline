package by.twitter.storage

import android.accounts.NetworkErrorException
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.twitter.network.model.TweetPayload
import by.twitter.network.TwitterService
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.util.Mapper
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TweetRepositoryImpl @Inject constructor(
        private val twitterService: TwitterService,
        private val appDatabase: AppDatabase
) : TweetRepository {

    private val executorService = Executors.newFixedThreadPool(1)

    override fun create(text: String): MutableLiveData<Boolean> {
        val requestEnd = MutableLiveData(false)
        twitterService
                .postUpdateTweet(text)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweetResponse = response.body()
                        if (tweetResponse != null) {
                            val tweet = Mapper.tweetPayloadToEntity(tweetResponse)
                            val user = Mapper.userPayloadToEntity(tweetResponse.user)
                            executorService.execute {
                                appDatabase.tweetDao().insertUser(user)
                                appDatabase.tweetDao().insertTweet(tweet)
                            }
                        }
                        Timber.i("New tweet: $tweetResponse")
                        requestEnd.value = true
                    }
                })
        return requestEnd
    }

    override fun homeTimeline(): LiveData<List<TweetWithUser>> {
        twitterService
                .getHomeTimeline()
                .enqueue(object : Callback<List<TweetPayload>> {
                    override fun onFailure(call: Call<List<TweetPayload>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<TweetPayload>>, response: retrofit2.Response<List<TweetPayload>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            val listTweetWithUser = tweetsList.map {
                                val tweet = Mapper.tweetPayloadToEntity(it)
                                val user = Mapper.userPayloadToEntity(it.user)
                                Pair(tweet, user)
                            }
                            executorService.execute {
                                listTweetWithUser.forEach { (tweet, user) ->
                                    appDatabase.tweetDao().insertUser(user)
                                    appDatabase.tweetDao().insertTweet(tweet)
                                }
                            }
                            Timber.i("Call result: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })
        return appDatabase.tweetDao().getAllWithUser()
    }

    override fun userTimeline(userId: Long): LiveData<List<TweetWithUser>> {
        twitterService
                .getUserTimeline(userId = userId)
                .enqueue(object : Callback<List<TweetPayload>> {
                    override fun onFailure(call: Call<List<TweetPayload>>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<List<TweetPayload>>, response: retrofit2.Response<List<TweetPayload>>) {
                        val tweetsList = response.body()
                        if (tweetsList != null) {
                            val listTweetWithUser = tweetsList.map {
                                val tweet = Mapper.tweetPayloadToEntity(it)
                                val user = Mapper.userPayloadToEntity(it.user)
                                Pair(tweet, user)
                            }
                            executorService.execute {
                                listTweetWithUser.forEach { (tweet, user) ->
                                    appDatabase.tweetDao().insertUser(user)
                                    appDatabase.tweetDao().insertTweet(tweet)
                                }
                            }
                            Timber.i("Call result user tweets: ${tweetsList.joinToString(separator = "\n")}")
                        }
                    }
                })
        return appDatabase.tweetDao().getAllByUserId(userId)
    }

    override fun retweet(id: Long) {
        twitterService
                .postRetweet(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweetResponse = response.body()
                        if (tweetResponse != null) {
                            val tweet = Mapper.tweetPayloadToEntity(tweetResponse)
                            val user = Mapper.userPayloadToEntity(tweetResponse.user)
                            executorService.execute {
                                appDatabase.tweetDao().insertUser(user)
                                appDatabase.tweetDao().insertTweet(tweet)
                            }
                        }
                        Timber.i("Retweet: $tweetResponse")
                    }
                })

    }

    override fun unretweet(id: Long) {
        twitterService
                .postUnretweet(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweetResponse = response.body()
                        if (tweetResponse != null) {
                            val tweet = Mapper.tweetPayloadToEntity(tweetResponse)
                            val user = Mapper.userPayloadToEntity(tweetResponse.user)
                            executorService.execute {
                                appDatabase.tweetDao().insertUser(user)
                                appDatabase.tweetDao().insertTweet(tweet)
                            }
                        }
                        Timber.i("Unretweet: $tweetResponse")
                    }
                })
    }

    override fun favoritesCreate(id: Long) {
        twitterService
                .postFavoritesCreate(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweetResponse = response.body()
                        if (tweetResponse != null) {
                            val tweet = Mapper.tweetPayloadToEntity(tweetResponse)
                            val user = Mapper.userPayloadToEntity(tweetResponse.user)
                            executorService.execute {
                                appDatabase.tweetDao().insertUser(user)
                                appDatabase.tweetDao().insertTweet(tweet)
                            }
                        }
                        Timber.i("Like tweet: $tweetResponse")
                    }
                })
    }

    override fun favoritesDestroy(id: Long) {
        twitterService
                .postFavoritesDestroy(id)
                .enqueue(object : Callback<TweetPayload> {
                    override fun onFailure(call: Call<TweetPayload>, t: Throwable) {
                        throw NetworkErrorException("Check network connection")
                    }

                    override fun onResponse(call: Call<TweetPayload>, response: retrofit2.Response<TweetPayload>) {
                        val tweetResponse = response.body()
                        if (tweetResponse != null) {
                            val tweet = Mapper.tweetPayloadToEntity(tweetResponse)
                            val user = Mapper.userPayloadToEntity(tweetResponse.user)
                            executorService.execute {
                                appDatabase.tweetDao().insertUser(user)
                                appDatabase.tweetDao().insertTweet(tweet)
                            }
                        }
                        Timber.i("Dislike tweet: $tweetResponse")
                    }
                })

    }

    override fun deleteTweet(tweet: Tweet) {
        executorService.execute {
            appDatabase.tweetDao().deleteTweet(tweet)
        }
    }

}