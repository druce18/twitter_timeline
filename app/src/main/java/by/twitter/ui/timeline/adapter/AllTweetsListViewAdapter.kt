package by.twitter.ui.timeline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.util.DateUtil
import kotlinx.android.synthetic.main.item_tweet.view.*

class AllTweetsListViewAdapter(
    private var context: Context,
    private var tweetsList: List<Tweet>
) : BaseAdapter() {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_tweet, null)

        val tweet = tweetsList[position]
        view.usernameTweetTextView.text = tweet.user.name
        view.timeTweetTextView.text = DateUtil.toSimpleString(tweet.date)
        view.userIDTweetTextView.text = tweet.user.nameID
        view.massageTweetTextView.text = tweet.massage
        view.answersCountTweetTextView.text = tweet.answers.toString()
        view.retweetsCountTweetTextView.text = tweet.retweets.toString()
        view.likesCountTweetTextView.text = tweet.likes.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return tweetsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return tweetsList.size
    }

}