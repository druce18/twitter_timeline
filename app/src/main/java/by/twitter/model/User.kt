package by.twitter.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("screen_name")
        val screenName: String,
        @SerializedName("profile_image_url_https")
        val profileImageUrlHttps: String
)