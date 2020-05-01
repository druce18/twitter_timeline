package by.twitter.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.storage.TweetRepository
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var tweetRepository: TweetRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

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

    companion object {

        fun newInstance(): Fragment = MainFragment()

    }

}