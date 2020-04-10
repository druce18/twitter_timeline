package by.twitter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.twitter.R
import by.twitter.model.Tweet
import kotlinx.android.synthetic.main.fragment_main_old.*
import kotlinx.android.synthetic.main.tool_bar.*

class OldMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_old, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameMenuTextView.text = getString(R.string.main)
        sendTweetButton.visibility = View.GONE
        newTweetButton.visibility = View.GONE

//        OldMainFragmentArgs.fromBundle(arguments!!).newTweet?.let {
//            tweets.add(0, it)
//            arguments!!.clear()
//        }
//
//
//        addTweetButton.setOnClickListener {
//            navigateToCreateTweet()
//        }
//
//        showTweetsButton.setOnClickListener {
//            navigateToAllTweets()
//        }
//
//        settingsButton.setOnClickListener {
//            navigateToSettings()
//        }

    }

//    private fun navigateToCreateTweet() {
//        val action = OldMainFragmentDirections.actionMainFragmentToCreateTweetFragment()
//        findNavController().navigate(action)
//    }
//
//    private fun navigateToAllTweets() {
//        val action = OldMainFragmentDirections.actionMainFragmentToAllTweetsFragment(tweets.toTypedArray())
//        findNavController().navigate(action)
//    }
//
//    private fun navigateToSettings() {
//        val action = OldMainFragmentDirections.actionMainFragmentToSettingsFragment()
//        findNavController().navigate(action)
//    }

    companion object {
        private val tweets = arrayListOf<Tweet>()
    }

}
