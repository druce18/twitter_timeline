package by.twitter.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.R
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*

class AllTweetsAdapter(private val tweetsList: List<TweetWithUser>,
                       private val userClickListener: (Long) -> Unit,
                       private val tweetClickListener: (Long) -> Unit,
                       private val likeClickListener: (Tweet, Int) -> Unit,
                       private val retweetClickListener: (Tweet, Int) -> Unit) :
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
        holder.bind(tweet, position, userClickListener, tweetClickListener, likeClickListener, retweetClickListener)
    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val requestManager = Glide.with(containerView.context)

        fun bind(tweetWithUser: TweetWithUser, position: Int,
                 userClickListener: (Long) -> Unit,
                 tweetClickListener: (Long) -> Unit,
                 likeClickListener: (Tweet, Int) -> Unit,
                 retweetClickListener: (Tweet, Int) -> Unit) {

            requestManager.clear(userProfileTweetImage)

            requestManager
                    .load(tweetWithUser.user.profileImageUrlHttps)
                    .circleCrop()
                    .into(userProfileTweetImage)

            usernameTweetTextView.text = tweetWithUser.user.name
            timeTweetTextView.text = DateUtil.printDateOnTweet(tweetWithUser.tweet.createdAt)
            userIDTweetTextView.text = "@${tweetWithUser.user.screenName}"
            massageTweetTextView.text = tweetWithUser.tweet.text
            retweetsCountMainTweetTextView.text = tweetWithUser.tweet.retweetCount.toString()
            likesCountTweetTextView.text = tweetWithUser.tweet.favoriteCount.toString()

            if (tweetWithUser.tweet.favorited) {
                likeTweetImageButton.setImageResource(R.drawable.ic_like_tweet)
            } else {
                likeTweetImageButton.setImageResource(R.drawable.ic_dislike_tweet)
            }

            if (tweetWithUser.tweet.retweeted) {
                retweetTweetImageButton.setImageResource(R.drawable.ic_retweet_arrows)
            } else {
                retweetTweetImageButton.setImageResource(R.drawable.ic_disretweet_arrows)
            }

            userProfileTweetImage.setOnClickListener {
                userClickListener(tweetWithUser.user.id)
            }

            tweetItem.setOnClickListener {
                tweetClickListener(tweetWithUser.tweet.id)
            }

            likeTweetImageButton.setOnClickListener {
                likeClickListener(tweetWithUser.tweet, position)
            }

            retweetTweetImageButton.setOnClickListener {
                retweetClickListener(tweetWithUser.tweet, position)
            }
        }
    }

    companion object {
        const val TYPE_TWEET = 0
    }
}