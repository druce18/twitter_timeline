package by.twitter.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = MainPageAdapter(this)

        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "HOME"
                1 -> "Create tweet"
                else -> throw IllegalArgumentException("position not found")
            }
        }.attach()

    }

}