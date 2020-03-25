package by.twitter.ui.timeline

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.storage.Tweets
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import by.twitter.util.RandomTweets
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        nameMenuTextView.text = getString(R.string.timeline)
//        sendTweetButton.visibility = View.GONE
//        newTweetButton.visibility = View.VISIBLE
//
        RandomTweets.addTweets()
        val tweetsList = Tweets.read()

        tweetsRecyclerView.adapter = AllTweetsAdapter(tweetsList.toList())
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        newTweetButton.setOnClickListener {
//            navigateToCreateTweet()
//        }
//
//        settingsButton.setOnClickListener {
//            navigateToSettings()
//        }
    }

//    private fun navigateToCreateTweet() {
//        val action = AllTweetsFragmentDirections.actionAllTweetsFragmentToCreateTweetFragment()
//        findNavController().navigate(action)
//    }
//
//    private fun navigateToSettings() {
//        val action = AllTweetsFragmentDirections.actionAllTweetsFragmentToSettingsFragment()
//        findNavController().navigate(action)
//    }

    companion object {

        fun newInstance(): Fragment = TimelineFragment()

    }
}
