package by.twitter.ui.timeline

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.model.TweetPayload
import by.twitter.model.UserPayload
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.profile.UserProfileFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var timelineViewModel: TimelineViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        timelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TimelineViewModel::class.java)

        subscribeTimelineViewModel()

        createTweetActionButton.setOnClickListener {
            navigateToCreateTweet()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun subscribeTimelineViewModel() {
        timelineViewModel.setTweetsForTimeline()
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
        timelineViewModel.user = user
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                        R.id.nav_controller,
                        UserProfileFragment.newInstance()
                )
                .addToBackStack(UserProfileFragment::class.java.simpleName)
                .commit()
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

        fun newInstance(): Fragment = TimelineFragment()

    }

}
