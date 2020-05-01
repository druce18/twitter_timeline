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
import by.twitter.storage.entity.User
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TweetTimelineFragment : Fragment(R.layout.fragment_timeline_tweet_profile) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var tweetTimelineViewModel: TweetTimelineViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tweetTimelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TweetTimelineViewModel::class.java)

        subscribeTimelineViewModel()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun subscribeTimelineViewModel() {
        tweetTimelineViewModel.setTweetsTimeline()
        val userOnClick: (User) -> Unit = { user -> navigateToUser(user) }
        val likeOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            tweetTimelineViewModel.likeOrDislikeTweet(tweet, position)
        }
        val retweetOnClick: (Tweet, Int) -> Unit = { tweet, position ->
            tweetTimelineViewModel.retweetOrUnretweet(tweet, position)
        }

        tweetTimelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets, userOnClick, likeOnClick, retweetOnClick)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//            tweetsRecyclerView.scrollToPosition(timelineViewModel.position)
        })
    }

    private fun navigateToUser(user: User) {
//        val mainFragment: MainFragment = parentFragment as MainFragment
//        mainFragment.navigateToUser(user.id)
    }


    companion object {

        fun newInstance(): Fragment = TimelineFragment()

    }

}