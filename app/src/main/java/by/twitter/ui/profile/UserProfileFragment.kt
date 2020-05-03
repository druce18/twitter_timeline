package by.twitter.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.model.TweetPayload
import by.twitter.model.UserPayload
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.timeline.TimelineViewModel
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.item_tweet.userProfileTweetImage
import kotlinx.android.synthetic.main.item_user_profile.*
import javax.inject.Inject

class UserProfileFragment : Fragment(R.layout.fragment_timeline_user) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var timelineViewModel: TimelineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TimelineViewModel::class.java)
        timelineViewModel.position = 0

        printUser()

        subscribeTimelineViewModel()

        createTweetActionButton.setOnClickListener {
            navigateToCreateTweet()
        }

        backImageButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun printUser() {
        val user = timelineViewModel.user

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

        if (!user.url_user.isNullOrEmpty()) {
            urlLinearLayout.visibility = View.VISIBLE
            urlUserTextView.text = user.url_user
        }

        if (!user.createdAt.isNullOrEmpty()) {
            registrationLinearLayout.visibility = View.VISIBLE
            registrationUserTextView.text = "Registration: ${DateUtil.toSimpleString(user.createdAt)}"
        }

        friendsCountUserTextView.text = user.friendsCount.toString()
        followersCountUserTextView.text = user.followersCount.toString()
    }

    private fun subscribeTimelineViewModel() {
        timelineViewModel.setTweetsForUser()
        val userOnClick: (UserPayload) -> Unit = { user -> navigateToUser(user) }
        val likeOnClick: (TweetPayload, Int) -> Unit = { tweet, position ->
            timelineViewModel.likeOrDislikeTweet(tweet, position)
        }
        val retweetOnClick: (TweetPayload, Int) -> Unit = { tweet, position ->
            timelineViewModel.retweetOrUnretweet(tweet, position)
        }

        timelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetPayload>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets, userOnClick, likeOnClick, retweetOnClick)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            tweetsRecyclerView.scrollToPosition(timelineViewModel.position)
        })
    }

    private fun navigateToUser(user: UserPayload) {
//        timelineViewModel.user = user
//        requireActivity().supportFragmentManager.beginTransaction()
//                .replace(
//                        R.id.nav_controller,
//                        newInstance()
//                )
//                .addToBackStack(UserProfileFragment::class.java.simpleName)
//                .commit()
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
