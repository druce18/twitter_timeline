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
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.item_tweet.userProfileTweetImage
import kotlinx.android.synthetic.main.item_user_profile.*
import javax.inject.Inject

class UserProfileFragment : Fragment(R.layout.fragment_timeline_user_profile) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private lateinit var userProfileViewModel: UserProfileViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserProfileViewModel::class.java)

        printUser()
        subscribeTimelineViewModel()

        createTweetActionButton.setOnClickListener {
            navigateToCreateTweet()
        }

        backImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun printUser() {
        userProfileViewModel.userId = UserProfileFragmentArgs.fromBundle(requireArguments()).userId
        userProfileViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            val requestManager = context?.let { Glide.with(it) }
            if (requestManager != null) {
                requestManager.clear(userProfileTweetImage)
                requestManager
                        .load(user.profileImageUrlHttps)
                        .circleCrop()
                        .into(userProfileTweetImage)

                requestManager.clear(userBackgroundImage)
                requestManager
                        .load(user.profileBannerUrl)
                        .into(userBackgroundImage)
            }

            usernameTextView.text = user.name
            userIDTextView.text = "@${user.screenName}"
            descriptionUserTextView.text = user.description

            if (!user.location.isNullOrEmpty()) {
                locationLinearLayout.visibility = View.VISIBLE
                locationUserTextView.text = user.location
            }

            if (!user.urlUser.isNullOrEmpty()) {
                urlLinearLayout.visibility = View.VISIBLE
                urlUserTextView.text = user.urlUser
            }

            if (!user.createdAt.isNullOrEmpty()) {
                registrationLinearLayout.visibility = View.VISIBLE
                registrationUserTextView.text = "Registration: ${DateUtil.printDateReg(user.createdAt)}"
            }

            friendsCountUserTextView.text = user.friendsCount.toString()
            followersCountUserTextView.text = user.followersCount.toString()
        })
    }

    private fun subscribeTimelineViewModel() {
        userProfileViewModel.setTweetsUserTimeline()
        val userOnClick: (Long) -> Unit = { userId -> navigateToUser(userId) }
        val tweetOnClick: (Long) -> Unit = { tweetId -> navigateToTweet(tweetId) }
        val likeOnClick: (Tweet) -> Unit = { tweet ->
            userProfileViewModel.likeOrDislikeTweet(tweet)
        }
        val retweetOnClick: (Tweet) -> Unit = { tweet ->
            userProfileViewModel.retweetOrUnretweet(tweet)
        }

        val tweetsAdapter = AllTweetsAdapter(listOf(), userOnClick, tweetOnClick, likeOnClick, retweetOnClick)
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        tweetsRecyclerView.adapter = tweetsAdapter
        userProfileViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsAdapter.tweetsList = tweets
            tweetsAdapter.notifyDataSetChanged()
        })
    }

    private fun navigateToUser(userId: Long) {}

    private fun navigateToTweet(tweetId: Long) {
        val action = UserProfileFragmentDirections.actionUserProfileFragmentToTweetProfileFragment(tweetId)
        findNavController().navigate(action)
    }

    private fun navigateToCreateTweet() {
        val action = UserProfileFragmentDirections.actionUserProfileFragmentToCreateTweetFragment()
        findNavController().navigate(action)
    }

}
