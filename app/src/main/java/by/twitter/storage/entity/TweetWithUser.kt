package by.twitter.storage.entity

import androidx.room.Embedded

data class TweetWithUser(
        @Embedded(prefix = "tweet_")
        val tweet: Tweet,
        @Embedded
        val user: User
)