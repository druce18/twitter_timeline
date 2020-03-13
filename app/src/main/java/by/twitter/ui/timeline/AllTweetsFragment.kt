package by.twitter.ui.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.storage.Tweets
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
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
        val tweetsList = Tweets.read()
        tweetsRecyclerView.adapter = AllTweetsAdapter(tweetsList)
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        fun newInstance(): Fragment = AllTweetsFragment()
    }

}
