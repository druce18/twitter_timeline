package by.twitter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.twitter.R
import by.twitter.model.Tweet
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.tool_bar.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameMenuTextView.text = getString(R.string.main)
        sendTweetButton.visibility = View.GONE
        newTweetButton.visibility = View.GONE

        MainFragmentArgs.fromBundle(arguments!!).newTweet?.let {
            tweets.add(0, it)
            arguments!!.clear()
        }


        addTweetButton.setOnClickListener {
            navigateToCreateTweet()
        }

        showTweetsButton.setOnClickListener {
            navigateToAllTweets()
        }

        settingsButton.setOnClickListener {
            navigateToSettings()
        }

    }

    private fun navigateToCreateTweet() {
        val action = MainFragmentDirections.actionMainFragmentToCreateTweetFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAllTweets() {
        val action = MainFragmentDirections.actionMainFragmentToAllTweetsFragment(tweets.toTypedArray())
        findNavController().navigate(action)
    }

    private fun navigateToSettings() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    companion object {
        private val tweets = arrayListOf<Tweet>()
    }

}
