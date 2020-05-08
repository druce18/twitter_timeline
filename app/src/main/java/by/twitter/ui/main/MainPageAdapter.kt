package by.twitter.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.twitter.ui.error.ErrorFragment
import by.twitter.ui.timeline.TimelineFragment

class MainPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TimelineFragment.newInstance()
            }
            1 -> {
                ErrorFragment.newInstance()
            }
            else -> throw IllegalArgumentException("no required position")
        }
    }

    override fun getItemCount(): Int = 2

}