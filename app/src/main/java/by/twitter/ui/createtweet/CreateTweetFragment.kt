package by.twitter.ui.createtweet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import by.twitter.storage.Tweets
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_tweet.*

class CreateTweetFragment : Fragment(R.layout.fragment_create_tweet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendTweetButton.setOnClickListener {
            saveTweet(view)
        }

    }

    private fun saveTweet(view: View) {
        val massage = massageTweetTextInputEditText.text.toString()
        if (massage.isNotEmpty()) {
            Tweets.create(massage)
        } else {
            Snackbar.make(view, R.string.wrong_input, Snackbar.LENGTH_LONG).show()
        }
    }


    companion object {

        fun newInstance(): Fragment = CreateTweetFragment()

    }

}
