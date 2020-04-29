package by.twitter.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.storage.entity.User

@Dao
interface TweetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweet: Tweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

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
          users.*
          FROM tweets 
         INNER JOIN users ON tweets.user_id=users.id
            """
    )
    fun getAllWithUser(): LiveData<List<TweetWithUser>>

}