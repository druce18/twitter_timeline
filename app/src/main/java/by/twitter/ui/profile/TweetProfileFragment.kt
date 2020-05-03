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
import by.twitter.ui.timeline.RetweetLikeViewModel
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
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
    private lateinit var retweetLikeViewModel: RetweetLikeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tweetProfileViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TweetProfileViewModel::class.java)
        retweetLikeViewModel = ViewModelProvider(this, viewModelProviderFactory).get(RetweetLikeViewModel::class.java)
        tweetProfileViewModel.tweetId = TweetProfileFragmentArgs.fromBundle(requireArguments()).tweetId

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
        tweetProfileViewModel.mainTweet().observe(viewLifecycleOwner, Observer { tweetWithUser ->
            val requestManager = context?.let { Glide.with(it) }
            if (requestManager != null) {
                requestManager.clear(userProfileTweetImage)
                requestManager
                        .load(tweetWithUser.user.profileImageUrlHttps)
                        .circleCrop()
                        .into(userProfileTweetImage)
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
                retweetLikeViewModel.likeOrDislikeTweet(tweetWithUser.tweet, 0)
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
        val likeOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            retweetLikeViewModel.likeOrDislikeTweet(tweet, position)
        }
        val retweetOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            retweetLikeViewModel.retweetOrUnretweet(tweet, position)
        }

        tweetProfileViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets, userOnClick, tweetOnClick, likeOnClick, retweetOnClick)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            tweetsRecyclerView.scrollToPosition(retweetLikeViewModel.position)
        })
    }

    private fun navigateToUser(userId: Long) {
        val action = TweetProfileFragmentDirections.actionTweetProfileFragmentToUserProfileFragment(userId)
        findNavController().navigate(action)
    }

    private fun navigateToTweet(tweetId: Long) {}

    companion object {

        fun newInstance(): Fragment = TweetProfileFragment()

    }

}