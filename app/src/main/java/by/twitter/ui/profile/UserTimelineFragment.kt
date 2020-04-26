package by.twitter.ui.profile

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.model.Tweet
import by.twitter.model.User
import by.twitter.storage.UserNow
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import by.twitter.util.DateUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.item_tweet.userProfileTweetImage
import kotlinx.android.synthetic.main.item_user_profile.*
import javax.inject.Inject

class UserTimelineFragment : Fragment(R.layout.fragment_timeline_user) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var userTimelineViewModel: UserTimelineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userTimelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserTimelineViewModel::class.java)

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
        val requestManager = context?.let { Glide.with(it) }
        if (requestManager != null) {
            requestManager.clear(userProfileTweetImage)
            requestManager
                    .load(UserNow.user?.profileImageUrlHttps)
                    .circleCrop()
                    .into(userProfileTweetImage)

            requestManager.clear(userBackgroundImage)
            requestManager
                    .load(UserNow.user?.profileBannerUrl)
                    .into(userBackgroundImage)
        }

        usernameTextView.text = UserNow.user?.name
        userIDTextView.text = "@${UserNow.user?.screenName}"
        descriptionUserTextView.text = UserNow.user?.description

        if (!UserNow.user?.location.isNullOrEmpty()) {
            locationLinearLayout.visibility = View.VISIBLE
            locationUserTextView.text = UserNow.user?.location
        }

        if (!UserNow.user?.url_user.isNullOrEmpty()) {
            urlLinearLayout.visibility = View.VISIBLE
//            urlUserTextView.movementMethod = LinkMovementMethod.getInstance()
            urlUserTextView.text = UserNow.user?.url_user
        }

        if (!UserNow.user?.createdAt.isNullOrEmpty()) {
            registrationLinearLayout.visibility = View.VISIBLE
            registrationUserTextView.text = DateUtil.toSimpleString(UserNow.user?.createdAt!!)
        }

        friendsCountUserTextView.text = UserNow.user?.friendsCount.toString()
        followersCountUserTextView.text = UserNow.user?.followersCount.toString()
    }

    private fun subscribeTimelineViewModel() {
        UserNow.user?.id?.let { userTimelineViewModel.updateTimeline(it) }
        userTimelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<Tweet>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets) { user ->
                navigateToUser(user)
            }
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        })
    }

    private fun navigateToUser(user: User) {
//        UserNow.user = user
//        requireActivity().supportFragmentManager.beginTransaction()
//                .replace(
//                        R.id.nav_controller,
//                        newInstance()
//                )
//                .addToBackStack(UserTimelineFragment::class.java.simpleName)
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

        fun newInstance(): Fragment = UserTimelineFragment()

    }
}
