package by.twitter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.twitter.model.Tweets
import kotlinx.android.synthetic.main.fragment_all_tweets.*
import kotlinx.android.synthetic.main.top_bar.*

class AllTweetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameMenuTextView.text = getString(R.string.tweets)
        tweetsTextView.text = Tweets.readTweets().toString()
    }

    companion object {
        fun newInstance(): Fragment = AllTweetsFragment()
    }

}
