package by.twitter.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("screen_name")
        val screenName: String,
        @SerializedName("profile_image_url_https")
        val profileImageUrlHttps: String
) : Parcelable