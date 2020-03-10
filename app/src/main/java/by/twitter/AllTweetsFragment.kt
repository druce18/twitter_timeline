package by.twitter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.twitter.model.TweetsSingleton
import kotlinx.android.synthetic.main.fragment_all_tweets.view.*
import kotlinx.android.synthetic.main.top_bar.view.*

class AllTweetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_tweets, container, false)
        view.nameMenuTextView.text = getString(R.string.tweets)
        view.tweetsTextView.text = TweetsSingleton.readTweets().toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): Fragment = AllTweetsFragment()
    }

}
