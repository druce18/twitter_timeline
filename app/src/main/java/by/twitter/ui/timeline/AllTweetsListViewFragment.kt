package by.twitter.ui.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.twitter.R
import by.twitter.storage.Tweets
import by.twitter.ui.timeline.adapter.AllTweetsListViewAdapter
import kotlinx.android.synthetic.main.fragment_all_tweets_list_view.*
import kotlinx.android.synthetic.main.top_bar.*

class AllTweetsListViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_tweets_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameMenuTextView.text = getString(R.string.tweets)
        val tweetsList = Tweets.read()
        tweetsListView.adapter = AllTweetsListViewAdapter(requireContext(), tweetsList)
    }

    companion object {
        fun newInstance(): Fragment = AllTweetsListViewFragment()
    }

}