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
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.timeline.RetweetLikeViewModel
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
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
    private lateinit var retweetLikeViewModel: RetweetLikeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserProfileViewModel::class.java)
        retweetLikeViewModel = ViewModelProvider(this, viewModelProviderFactory).get(RetweetLikeViewModel::class.java)
        userProfileViewModel.userId = UserProfileFragmentArgs.fromBundle(requireArguments()).userId

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
                registrationUserTextView.text = "Registration: ${DateUtil.getDateReg(user.createdAt)}"
            }

            friendsCountUserTextView.text = user.friendsCount.toString()
            followersCountUserTextView.text = user.followersCount.toString()
        })
    }

    private fun subscribeTimelineViewModel() {
        userProfileViewModel.setTweetsUserTimeline()
        val userOnClick: (Long) -> Unit = { userId -> navigateToUser(userId) }
        val tweetOnClick: (Long) -> Unit = { tweetId -> navigateToTweet(tweetId) }
        val likeOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            retweetLikeViewModel.likeOrDislikeTweet(tweet, position)
        }
        val retweetOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            retweetLikeViewModel.retweetOrUnretweet(tweet, position)
        }

        userProfileViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets, userOnClick, tweetOnClick, likeOnClick, retweetOnClick)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            tweetsRecyclerView.scrollToPosition(userProfileViewModel.position)
        })
    }

    private fun navigateToUser(userId: Long) {}

    private fun navigateToTweet(tweetId: Long) {
        val action = UserProfileFragmentDirections.actionUserProfileFragmentToTweetProfileFragment(tweetId)
        findNavController().navigate(action)
    }

    private fun navigateToCreateTweet() {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                        R.id.nav_controller,
                        CreateTweetFragment.newInstance()
                )
                .addToBackStack(CreateTweetFragment::class.java.simpleName)
                .commit()
    }

    companion object {

        fun newInstance(): Fragment = UserProfileFragment()

    }
}
