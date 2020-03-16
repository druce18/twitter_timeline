package by.twitter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.twitter.ui.timeline.AllTweetsFragment
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.R
import by.twitter.ui.timeline.AllTweetsListViewFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTweetButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContent,
                    CreateTweetFragment.newInstance()
                )
                .addToBackStack("Add Tweet")
                .commit()
        }

        showTweetsButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContent,
                    AllTweetsFragment.newInstance()
                )
                .addToBackStack("Show Tweets RecyclerView")
                .commit()
        }

        showTweetsListViewButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContent,
                    AllTweetsListViewFragment.newInstance()
                )
                .addToBackStack("Show Tweets ListView")
                .commit()
        }
    }

    companion object {
        fun newInstance(): Fragment = MainFragment()
    }
}
