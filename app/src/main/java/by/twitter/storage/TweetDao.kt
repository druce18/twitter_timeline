package by.twitter.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.storage.entity.User


@Dao
interface TweetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTweet(tweet: Tweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteTweet(tweet: Tweet)

    @Query("SELECT * FROM tweets")
    fun getAll(): LiveData<List<Tweet>>

    @Query(
            """
         SELECT 
          tweets.id as tweet_id,
          tweets.created_at as tweet_created_at,
          tweets.text as tweet_text,
          tweets.retweet_count as tweet_retweet_count,
          tweets.retweeted as tweet_retweeted,
          tweets.favorite_count as tweet_favorite_count,
          tweets.favorited as tweet_favorited,
          tweets.user_id as tweet_user_id,
          tweets.quoted_status_id as tweet_quoted_status_id,
          tweets.is_quote_status as tweet_is_quote_status,
          tweets.in_reply_to_status_id as tweet_in_reply_to_status_id,
          users.*
          FROM tweets 
         INNER JOIN users ON tweets.user_id=users.id
         WHERE tweets.in_reply_to_status_id=:inReplyToStatusId
         ORDER BY tweet_created_at DESC 
            """
    )
    fun getAllWithUser(inReplyToStatusId: Long = 0): LiveData<List<TweetWithUser>>

    @Query("SELECT * FROM users WHERE users.id=:userId")
    fun getUserById(userId: Long): LiveData<User>

    @Query(
            """
         SELECT 
          tweets.id as tweet_id,
          tweets.created_at as tweet_created_at,
          tweets.text as tweet_text,
          tweets.retweet_count as tweet_retweet_count,
          tweets.retweeted as tweet_retweeted,
          tweets.favorite_count as tweet_favorite_count,
          tweets.favorited as tweet_favorited,
          tweets.user_id as tweet_user_id,
          tweets.quoted_status_id as tweet_quoted_status_id,
          tweets.is_quote_status as tweet_is_quote_status,
          tweets.in_reply_to_status_id as tweet_in_reply_to_status_id,
          users.*
          FROM tweets 
         INNER JOIN users ON tweets.user_id=users.id
         WHERE users.id=:userId AND tweets.in_reply_to_status_id=:inReplyToStatusId
         ORDER BY tweet_created_at DESC
            """
    )
    fun getAllByUserId(userId: Long, inReplyToStatusId: Long = 0): LiveData<List<TweetWithUser>>

    @Query(
            """
         SELECT 
          tweets.id as tweet_id,
          tweets.created_at as tweet_created_at,
          tweets.text as tweet_text,
          tweets.retweet_count as tweet_retweet_count,
          tweets.retweeted as tweet_retweeted,
          tweets.favorite_count as tweet_favorite_count,
          tweets.favorited as tweet_favorited,
          tweets.user_id as tweet_user_id,
          tweets.quoted_status_id as tweet_quoted_status_id,
          tweets.is_quote_status as tweet_is_quote_status,
          tweets.in_reply_to_status_id as tweet_in_reply_to_status_id,
          users.*
          FROM tweets 
         INNER JOIN users ON tweets.user_id=users.id
         WHERE tweets.id=:tweetId
         ORDER BY tweet_created_at DESC
            """
    )
    fun getTweetWithUserByTweetId(tweetId: Long): LiveData<TweetWithUser>

}