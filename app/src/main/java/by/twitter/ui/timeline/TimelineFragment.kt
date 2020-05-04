package by.twitter.ui.timeline

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.databinding.FragmentTimelineBinding
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.TweetWithUser
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapterBinding
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var fragmentTimelineBinding: FragmentTimelineBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentTimelineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false)
        return fragmentTimelineBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TimelineViewModel::class.java)
        timelineViewModel.mainFragment = parentFragment as MainFragment

        fragmentTimelineBinding.viewModel = timelineViewModel
        fragmentTimelineBinding.lifecycleOwner = this

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

        timelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<TweetWithUser>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapterBinding(tweets, userOnClick, tweetOnClick, timelineViewModel)
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
        val mainFragment: MainFragment = parentFragment as MainFragment
        mainFragment.navigateToCreateTweet()
    }

    companion object {

        fun newInstance(): Fragment = TimelineFragment()

    }

}
