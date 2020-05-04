package by.twitter.ui.timeline

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private lateinit var timelineViewModel: TimelineViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val userOnClick: (Long) -> Unit = { userId -> navigateToUser(userId) }
        val tweetOnClick: (Long) -> Unit = { tweetId -> navigateToTweet(tweetId) }
        val likeOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            timelineViewModel.likeOrDislikeTweet(tweet, position)
        }
        val retweetOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            timelineViewModel.retweetOrUnretweet(tweet, position)
        }

        timelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets, userOnClick, tweetOnClick, likeOnClick, retweetOnClick)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            tweetsRecyclerView.scrollToPosition(timelineViewModel.position)
        })
    }

    private fun navigateToUser(userId: Long) {
        val mainFragment: MainFragment = parentFragment as MainFragment
        mainFragment.navigateToUser(userId)
    }

    private fun navigateToTweet(tweetId: Long) {
        val mainFragment: MainFragment = parentFragment as MainFragment
        mainFragment.navigateToTweet(tweetId)
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
