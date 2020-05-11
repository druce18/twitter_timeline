package by.twitter.ui.timeline

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.databinding.FragmentTimelineBinding
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.adapter.AllTweetsBindingAdapter
import javax.inject.Inject

class TimelineFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var fragmentTimelineBinding: FragmentTimelineBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentTimelineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false)
        return fragmentTimelineBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timelineViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TimelineViewModel::class.java)
        timelineViewModel.mainFragment = parentFragment as MainFragment

        val adapter = AllTweetsBindingAdapter(listOf(), timelineViewModel)
        fragmentTimelineBinding.apply {
            viewModel = timelineViewModel
            lifecycleOwner = this@TimelineFragment
            executePendingBindings()
            tweetsRecyclerView.layoutManager = LinearLayoutManager(context)
            tweetsRecyclerView.adapter = adapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    companion object {
        fun newInstance(): Fragment = TimelineFragment()
    }

}
