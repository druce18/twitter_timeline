package by.twitter.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_timeline_tweet_profile.*
import kotlinx.android.synthetic.main.item_tweet.massageTweetTextView
import kotlinx.android.synthetic.main.item_tweet.timeTweetTextView
import kotlinx.android.synthetic.main.item_tweet.userProfileTweetImage
import kotlinx.android.synthetic.main.item_tweet_profile.*
import kotlinx.android.synthetic.main.item_tweet_profile.likeTweetImageButton
import kotlinx.android.synthetic.main.item_tweet_profile.retweetTweetImageButton
import kotlinx.android.synthetic.main.item_tweet_profile.retweetsCountMainTweetTextView
import kotlinx.android.synthetic.main.item_user_profile.backImageButton
import kotlinx.android.synthetic.main.item_user_profile.userIDTextView
import kotlinx.android.synthetic.main.item_user_profile.usernameTextView
import kotlinx.android.synthetic.main.tool_bar.*
import javax.inject.Inject

class TweetProfileFragment : Fragment(R.layout.fragment_timeline_tweet_profile) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private lateinit var tweetProfileViewModel: TweetProfileViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tweetProfileViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TweetProfileViewModel::class.java)

        nameMenuTextView.text = getString(R.string.tweet)

        backImageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        printTweet()
        subscribeTimelineViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun printTweet() {
        tweetProfileViewModel.tweetId = TweetProfileFragmentArgs.fromBundle(requireArguments()).tweetId
        tweetProfileViewModel.mainTweet().observe(viewLifecycleOwner, Observer { tweetWithUser ->
            val requestManager = context?.let { Glide.with(it) }
            if (requestManager != null) {
                requestManager.clear(userProfileTweetImage)
                requestManager
                        .load(tweetWithUser.user.profileImageUrlHttps)
                        .circleCrop()
                        .into(userProfileTweetImage)
            }
            userProfileTweetImage.setOnClickListener {
                navigateToUser(tweetWithUser.user.id)
            }

            usernameTextView.text = tweetWithUser.user.name
            userIDTextView.text = "@${tweetWithUser.user.screenName}"
            massageTweetTextView.text = tweetWithUser.tweet.text
            timeTweetTextView.text = "${DateUtil.printFullDateTweet(tweetWithUser.tweet.createdAt)}"
            retweetsCountMainTweetTextView.text = tweetWithUser.tweet.retweetCount.toString()
            favoritesCountMainTweetTextView.text = tweetWithUser.tweet.favoriteCount.toString()

            if (tweetWithUser.tweet.favorited) {
                likeTweetImageButton.setImageResource(R.drawable.ic_like_tweet)
            } else {
                likeTweetImageButton.setImageResource(R.drawable.ic_dislike_tweet)
            }
            likeTweetImageButton.setOnClickListener {
                tweetProfileViewModel.likeOrDislikeTweet(tweetWithUser.tweet)
            }

            if (tweetWithUser.tweet.retweeted) {
                retweetTweetImageButton.setImageResource(R.drawable.ic_retweet_arrows)
            } else {
                retweetTweetImageButton.setImageResource(R.drawable.ic_disretweet_arrows)
            }
        })
    }

    private fun subscribeTimelineViewModel() {
        tweetProfileViewModel.setTweetsTimeline()
        val userOnClick: (Long) -> Unit = { userId -> navigateToUser(userId) }
        val tweetOnClick: (Long) -> Unit = { tweetId -> navigateToTweet(tweetId) }
        val likeOnClick: (Tweet) -> Unit = { tweet ->
            tweetProfileViewModel.likeOrDislikeTweet(tweet)
        }
        val retweetOnClick: (Tweet) -> Unit = { tweet ->
            tweetProfileViewModel.retweetOrUnretweet(tweet)
        }

        val tweetsAdapter = AllTweetsAdapter(listOf(), userOnClick, tweetOnClick, likeOnClick, retweetOnClick)
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        tweetsRecyclerView.adapter = tweetsAdapter
        tweetProfileViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsAdapter.tweetsList = tweets
            tweetsAdapter.notifyDataSetChanged()
        })
    }

    private fun navigateToUser(userId: Long) {
        val action = TweetProfileFragmentDirections.actionTweetProfileFragmentToUserProfileFragment(userId)
        findNavController().navigate(action)
    }

    private fun navigateToTweet(tweetId: Long) {}

}