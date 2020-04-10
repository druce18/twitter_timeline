package by.twitter.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
        @SerializedName("favorite_count")
        val favoriteCount: Int
) : Parcelable