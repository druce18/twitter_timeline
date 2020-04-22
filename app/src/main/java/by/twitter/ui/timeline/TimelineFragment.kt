package by.twitter.ui.timeline

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeTimelineViewModel()

        createTweetActionButton.setOnClickListener {
            navigateToCreateTweet()
        }

    }

    private fun subscribeTimelineViewModel() {
        val model: TimelineViewModel by viewModels()

        model.getTweets().observe(viewLifecycleOwner, Observer<List<Tweet>> { tweets ->
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
