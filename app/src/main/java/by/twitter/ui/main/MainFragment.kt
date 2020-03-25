package by.twitter.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.IllegalArgumentException


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = MainPageAdapter(this)

        TabLayoutMediator(tab, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "HOME"
                1 -> tab.text = "Search"
                2 -> tab.text = "Direct Massages"
                else -> throw IllegalArgumentException("no required position")
            }
        }.attach()

    }

}