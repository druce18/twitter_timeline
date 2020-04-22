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
import by.twitter.model.Tweet
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var timelineViewModel: TimelineViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TimelineViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)

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
        timelineViewModel.getTweets().observe(viewLifecycleOwner, Observer<List<Tweet>> { tweets ->
            tweetsRecyclerView.adapter = AllTweetsAdapter(tweets)
            tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        })
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
