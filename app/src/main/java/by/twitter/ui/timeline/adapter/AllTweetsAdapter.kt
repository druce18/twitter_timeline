package by.twitter.ui.timeline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.util.DateUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*
import java.lang.IllegalArgumentException

class AllTweetsAdapter(private val tweetsList: List<Tweet>) :
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
        return tweetsList[position].type
    }

    override fun getItemCount(): Int {
        return tweetsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweetsList[position]
        holder.bind(tweet)
    }

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(tweet: Tweet) {
            when (tweet.type) {
                TYPE_TWEET -> {
                    usernameTweetTextView.text = tweet.user.name
                    timeTweetTextView.text = DateUtil.toSimpleString(tweet.date)
                    userIDTweetTextView.text = tweet.user.nameID
                    massageTweetTextView.text = tweet.massage
                    answersCountTweetTextView.text = tweet.answers.toString()
                    retweetsCountTweetTextView.text = tweet.retweets.toString()
                    likesCountTweetTextView.text = tweet.likes.toString()
                }
                TYPE_ADVERTISEMENT -> {
                }
            }
        }
    }

    companion object {
        const val TYPE_TWEET = 0
        const val TYPE_ADVERTISEMENT = 1
    }
}