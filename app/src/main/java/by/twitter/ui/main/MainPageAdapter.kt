package by.twitter.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.twitter.ui.dm.DirectMassageFragment
import by.twitter.ui.search.SearchFragment
import by.twitter.ui.timeline.TimelineFragment
import java.lang.IllegalArgumentException

class MainPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TimelineFragment()
            }
            1 -> {
                SearchFragment()
            }
            2 -> {
                DirectMassageFragment()
            }
            else -> throw IllegalArgumentException("no required position")
        }
    }

    override fun getItemCount(): Int = 3

}