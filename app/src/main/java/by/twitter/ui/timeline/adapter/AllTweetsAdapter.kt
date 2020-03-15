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

class AllTweetsAdapter(private val tweetsList: List<Tweet>) :
    RecyclerView.Adapter<AllTweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tweet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
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
            usernameTweetTextView.text = tweet.user.name
            timeTweetTextView.text = DateUtil.toSimpleString(tweet.date)
            userIDTweetTextView.text = tweet.user.nameID
            massageTweetTextView.text = tweet.massage
            answersCountTweetTextView.text = tweet.answers.toString()
            retweetsCountTweetTextView.text = tweet.retweets.toString()
            likesCountTweetTextView.text = tweet.likes.toString()
        }
    }

}