package by.twitter.ui.timeline

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.storage.Tweets
import by.twitter.ui.timeline.adapter.AllTweetsAdapter
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val tweetsList = Tweets.read()
//        tweetsRecyclerView.adapter = AllTweetsAdapter(tweetsList.toList())
//        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }


    override fun onResume() {
        super.onResume()
        val tweetsList = Tweets.read()
        tweetsRecyclerView.adapter = AllTweetsAdapter(tweetsList.toList())
        tweetsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {

        fun newInstance(): Fragment = TimelineFragment()

    }

}
