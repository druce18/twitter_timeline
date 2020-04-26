package by.twitter.ui.timeline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.model.User
import by.twitter.storage.UserNow
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*

class AllTweetsAdapter(private val tweetsList: List<Tweet>, private val clickListener: (User) -> Unit) :
        RecyclerView.Adapter<AllTweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = when (viewType) {
            TYPE_TWEET -> inflater.inflate(R.layout.item_tweet, parent, false)

            TYPE_ADVERTISEMENT -> inflater.inflate(R.layout.item_tweet_advertisement, parent, false)

            else -> (throw  IllegalArgumentException("Invalid view type"))
        }
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_TWEET
    }

    override fun getItemCount(): Int {
        return tweetsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweetsList[position]
        holder.bind(tweet, clickListener)
    }

    class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val requestManager = Glide.with(containerView.context)

        fun bind(tweet: Tweet, clickListener: (User) -> Unit) {
            requestManager.clear(userProfileTweetImage)

            requestManager
                    .load(tweet.user.profileImageUrlHttps)
                    .circleCrop()
                    .into(userProfileTweetImage)

            usernameTweetTextView.text = tweet.user.name
            timeTweetTextView.text = DateUtil.toSimpleString(tweet.createdAt)
            userIDTweetTextView.text = "@${tweet.user.screenName}"
            massageTweetTextView.text = tweet.text
            retweetsCountTweetTextView.text = tweet.retweetCount.toString()
            likesCountTweetTextView.text = tweet.favoriteCount.toString()

            userProfileTweetImage.setOnClickListener {
                clickListener(tweet.user)
            }

        }
    }

    companion object {
        const val TYPE_TWEET = 0
        const val TYPE_ADVERTISEMENT = 1
    }
}