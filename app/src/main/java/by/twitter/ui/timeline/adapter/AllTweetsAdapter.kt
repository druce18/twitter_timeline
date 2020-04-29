package by.twitter.ui.timeline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.R
import by.twitter.network.model.TweetPayload
import by.twitter.network.model.UserPayload
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*

class AllTweetsAdapter(private val tweetsList: List<TweetPayload>,
                       private val userClickListener: (UserPayload) -> Unit,
                       private val likeClickListener: (TweetPayload, Int) -> Unit,
                       private val retweetClickListener: (TweetPayload, Int) -> Unit) :
        RecyclerView.Adapter<AllTweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = when (viewType) {
            TYPE_TWEET -> inflater.inflate(R.layout.item_tweet, parent, false)

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
        holder.bind(tweet, position, userClickListener, likeClickListener, retweetClickListener)
    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val requestManager = Glide.with(containerView.context)

        fun bind(tweet: TweetPayload, position: Int,
                 userClickListener: (UserPayload) -> Unit,
                 likeClickListener: (TweetPayload, Int) -> Unit,
                 retweetClickListener: (TweetPayload, Int) -> Unit) {

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

            if (tweet.favorited) {
                likeTweetImageButton.setImageResource(R.drawable.ic_like_tweet)
            } else {
                likeTweetImageButton.setImageResource(R.drawable.ic_dislike_tweet)
            }

            if (tweet.retweeted) {
                retweetTweetImageButton.setImageResource(R.drawable.ic_retweet_arrows)
            } else {
                retweetTweetImageButton.setImageResource(R.drawable.ic_disretweet_arrows)
            }

            userProfileTweetImage.setOnClickListener {
                userClickListener(tweet.user)
            }

            likeTweetImageButton.setOnClickListener {
                likeClickListener(tweet, position)
            }

            retweetTweetImageButton.setOnClickListener {
                retweetClickListener(tweet, position)
            }
        }
    }

    companion object {
        const val TYPE_TWEET = 0
    }
}