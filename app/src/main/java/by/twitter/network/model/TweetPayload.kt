package by.twitter.network.model

import com.google.gson.annotations.SerializedName

data class TweetPayload(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Long,
        @SerializedName("text")
        val text: String,
        @SerializedName("user")
        val user: UserPayload,
        @SerializedName("retweet_count")
        val retweetCount: Int,
        @SerializedName("retweeted")
        val retweeted: Boolean,
        @SerializedName("favorite_count")
        val favoriteCount: Int,
        @SerializedName("favorited")
        val favorited: Boolean,
        @SerializedName("quoted_status_id")
        val quotedStatusId: Long,
        @SerializedName("is_quote_status")
        val isQuoteStatus: Boolean,
        @SerializedName("in_reply_to_status_id")
        val inReplyToStatusId: Long
)