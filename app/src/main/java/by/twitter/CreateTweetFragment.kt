package by.twitter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.twitter.model.Tweets
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_tweet.*
import kotlinx.android.synthetic.main.top_bar.*

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

        sendTweetButton.setOnClickListener {
            Log.i(CreateTweetFragment.toString(), "Run send tweet ")
            saveTweet(view)
        }
    }

    private fun saveTweet(view: View) {
        val username = usernameTweetTextInputEditText.text.toString()
        val massage = massageTweetTextInputEditText.text.toString()
        if (username.isNotEmpty() && massage.isNotEmpty()) {
            val tweet = Tweets.newTweet(username, massage)
            Tweets.createTweet(tweet)
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .replace(R.id.mainContent, MainFragment.newInstance())
                .commit()
        } else {
            Snackbar.make(view, R.string.wrong_input, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance(): Fragment = CreateTweetFragment()
    }

}
