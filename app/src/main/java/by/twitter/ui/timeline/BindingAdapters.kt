package by.twitter.ui.timeline

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import by.twitter.R
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.timeline.adapter.AllTweetsAdapterBinding
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import java.time.OffsetDateTime

@BindingAdapter("app:dateTweet")
fun dateTweet(textView: TextView, offsetDataTime: OffsetDateTime) {
    textView.text = DateUtil.printDateOnTweet(offsetDataTime)
}

@BindingAdapter("app:countToString")
fun countToString(textView: TextView, count: Int) {
    textView.text = count.toString()
}

@BindingAdapter("app:setImageUser")
fun setImageUser(imageView: ImageView, url: String) {
    val requestManager = Glide.with(imageView)
    requestManager.clear(imageView)
    requestManager
            .load(url)
            .circleCrop()
            .into(imageView)
}

@BindingAdapter("app:setLikeTweet")
fun setLikeTweet(imageButton: ImageButton, favorited: Boolean) {
    if (favorited) {
        imageButton.setImageResource(R.drawable.ic_like_tweet)
    } else {
        imageButton.setImageResource(R.drawable.ic_dislike_tweet)
    }
}

@BindingAdapter("app:setRetweetTweet")
fun setRetweetTweet(imageButton: ImageButton, retweeted: Boolean) {
    if (retweeted) {
        imageButton.setImageResource(R.drawable.ic_retweet_arrows)
    } else {
        imageButton.setImageResource(R.drawable.ic_disretweet_arrows)
    }
}

//@BindingAdapter("app:items")
//fun setItems(recyclerView: RecyclerView, items: List<TweetWithUser>) {
//    (recyclerView.adapter as AllTweetsAdapterBinding).tweetsList = items
//}