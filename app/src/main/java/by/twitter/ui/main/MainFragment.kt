package by.twitter.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.twitter.R
import by.twitter.TwitterApplication
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = MainPageAdapter(this)

        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "HOME"
                1 -> "Direct Massage"
                else -> throw IllegalArgumentException("position not found")
            }
        }.attach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    fun navigateToUser(userId: Long) {
        val action = MainFragmentDirections.actionFragmentMainToUserProfileFragment(userId)
        findNavController().navigate(action)
    }

    fun navigateToTweet(tweetId: Long) {
        val action = MainFragmentDirections.actionFragmentMainToTweetTimelineFragment(tweetId)
        findNavController().navigate(action)
    }

    fun navigateToCreateTweet() {
        val action = MainFragmentDirections.actionFragmentMainToCreateTweetFragment()
        findNavController().navigate(action)
    }

    companion object {

        fun newInstance(): Fragment = MainFragment()

    }

}