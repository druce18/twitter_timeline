package by.twitter.ui.timeline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.databinding.ItemTweetBinding
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.timeline.TimelineViewModel

class AllTweetsBindingAdapter(private var tweetsList: List<TweetWithUser>,
                              private val timelineViewModel: TimelineViewModel) :
        RecyclerView.Adapter<AllTweetsBindingAdapter.ViewHolder>(), DataAdapterBindable<List<TweetWithUser>> {

    override fun setData(data: List<TweetWithUser>) {
        tweetsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTweetBinding: ItemTweetBinding = ItemTweetBinding.inflate(inflater, parent, false)
        return ViewHolder(itemTweetBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return tweetsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweetsList[position]
        holder.bind(tweet, timelineViewModel)
    }


    class ViewHolder(private val itemTweetBinding: ItemTweetBinding) : RecyclerView.ViewHolder(itemTweetBinding.root) {

        fun bind(tweetUser: TweetWithUser, timelineViewModel: TimelineViewModel) {
            itemTweetBinding.apply {
                tweetWithUser = tweetUser
                itemTweetBinding.executePendingBindings()

                userProfileTweetImage.setOnClickListener {
                    timelineViewModel.mainFragment.navigateToUser(tweetUser.user.id)
                }

                tweetItem.setOnClickListener {
                    timelineViewModel.mainFragment.navigateToTweet(tweetUser.tweet.id)
                }

                likeTweetImageButton.setOnClickListener {
                    timelineViewModel.likeOrDislikeTweet(tweetUser.tweet)
                }

                retweetTweetImageButton.setOnClickListener {
                    timelineViewModel.retweetOrUnretweet(tweetUser.tweet)
                }
            }
        }
    }

}