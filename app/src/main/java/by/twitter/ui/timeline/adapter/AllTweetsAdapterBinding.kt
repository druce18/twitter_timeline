package by.twitter.ui.timeline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.twitter.databinding.ItemTweetBinding
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.timeline.TimelineViewModel

class AllTweetsAdapterBinding(private val tweetsList: List<TweetWithUser>,
                              private val userClickListener: (Long) -> Unit,
                              private val tweetClickListener: (Long) -> Unit,
                              private val timelineViewModel: TimelineViewModel) :
        RecyclerView.Adapter<AllTweetsAdapterBinding.ViewHolder>() {

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
        holder.bind(tweet, position, userClickListener, tweetClickListener, timelineViewModel)
    }


    class ViewHolder(private val itemTweetBinding: ItemTweetBinding) : RecyclerView.ViewHolder(itemTweetBinding.root) {

        fun bind(tweetWithUser: TweetWithUser,
                 position: Int,
                 userClickListener: (Long) -> Unit,
                 tweetClickListener: (Long) -> Unit,
                 timelineViewModel: TimelineViewModel) {

            itemTweetBinding.tweetWithUser = tweetWithUser
            itemTweetBinding.executePendingBindings()


            itemTweetBinding.userProfileTweetImage.setOnClickListener {
                userClickListener(tweetWithUser.user.id)
            }

            itemTweetBinding.tweetItem.setOnClickListener {
                tweetClickListener(tweetWithUser.tweet.id)
            }

            itemTweetBinding.likeTweetImageButton.setOnClickListener {
                timelineViewModel.likeOrDislikeTweet(tweetWithUser.tweet, position)
            }

            itemTweetBinding.retweetTweetImageButton.setOnClickListener {
                timelineViewModel.retweetOrUnretweet(tweetWithUser.tweet, position)
            }
        }
    }

}