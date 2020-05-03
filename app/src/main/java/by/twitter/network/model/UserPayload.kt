package by.twitter.network.model

import com.google.gson.annotations.SerializedName

data class UserPayload(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("screen_name")
        val screenName: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("url")
        val urlUser: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("friends_count")
        val friendsCount: Int,
        @SerializedName("followers_count")
        val followersCount: Int,
        @SerializedName("profile_image_url_https")
        val profileImageUrlHttps: String,
        @SerializedName("profile_banner_url")
        val profileBannerUrl: String
)