package by.twitter.ui.createtweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.storage.Tweets
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_tweet.*
import kotlinx.android.synthetic.main.tool_bar.*

class CreateTweetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_tweet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameMenuTextView.text = getString(R.string.new_tweet)
        sendTweetButton.visibility = View.VISIBLE
        newTweetButton.visibility = View.GONE

        sendTweetButton.setOnClickListener {
            saveTweet(view)
        }

        settingsButton.setOnClickListener {
            navigateToSettings()
        }
    }

    private fun saveTweet(view: View) {
        val username = usernameTweetTextInputEditText.text.toString()
        val massage = massageTweetTextInputEditText.text.toString()
        if (username.isNotEmpty() && massage.isNotEmpty()) {
            val tweet = Tweets.newTweet(username, massage)
            navigateBackToMain(tweet)
        } else {
            Snackbar.make(view, R.string.wrong_input, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun navigateToSettings() {
        val action = CreateTweetFragmentDirections.actionCreateTweetFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun navigateBackToMain(tweet: Tweet) {
        val action = CreateTweetFragmentDirections.actionCreateTweetFragmentToMainFragment(tweet)
        findNavController().navigate(action)
    }

}
