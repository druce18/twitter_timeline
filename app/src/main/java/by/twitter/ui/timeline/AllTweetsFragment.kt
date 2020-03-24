package by.twitter.ui.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_all_tweets.*
import kotlinx.android.synthetic.main.tool_bar.*

class AllTweetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameMenuTextView.text = getString(R.string.timeline)
        sendTweetButton.visibility = View.GONE
        newTweetButton.visibility = View.VISIBLE

        val tweetsList = AllTweetsFragmentArgs.fromBundle(arguments!!).tweets

        tweetsRecyclerView.adapter = AllTweetsAdapter(tweetsList.toList())
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        newTweetButton.setOnClickListener {
            navigateToCreateTweet()
        }

        settingsButton.setOnClickListener {
            navigateToSettings()
        }
    }

    private fun navigateToCreateTweet() {
        val action = AllTweetsFragmentDirections.actionAllTweetsFragmentToCreateTweetFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSettings() {
        val action = AllTweetsFragmentDirections.actionAllTweetsFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

}
