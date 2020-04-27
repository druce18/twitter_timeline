package by.twitter.model

import com.google.gson.annotations.SerializedName

data class Tweet(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Long,
        @SerializedName("text")
        val text: String,
        @SerializedName("user")
        val user: User,
        @SerializedName("retweet_count")
        val retweetCount: Int,
        @SerializedName("retweeted")
        val retweeted: Boolean,
        @SerializedName("favorite_count")
        val favoriteCount: Int,
        @SerializedName("favorited")
        val favorited: Boolean
)