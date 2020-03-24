package by.twitter.model

import android.os.Parcelable
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Tweet(
    val user: User = User(),
    val date: Date = Date(),
    val massage: String = "massage",
    val answers: Int = 0,
    val retweets: Int = 0,
    val likes: Int = 0,
    val type: Int = AllTweetsAdapter.TYPE_TWEET
) : Parcelable {
    override fun toString(): String {
        return "\n$user: massage = $massage"
    }
}