package by.twitter.ui.createtweet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.twitter.R
import by.twitter.storage.Tweets
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_tweet.*

class CreateTweetFragment : Fragment(R.layout.fragment_create_tweet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeViewModel()

    }

    private fun subscribeViewModel() {
        val viewModel: CreateTweetViewModel by viewModels()
        viewModel.loading.observe(viewLifecycleOwner, Observer<Boolean?> {
            if (it == null) return@Observer
            if (it) {
                progressBar.visibility = View.VISIBLE
                tweetFormMaterialCardView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tweetFormMaterialCardView.visibility = View.VISIBLE
            }
        })

        viewModel.goBackToTimeline.observe(viewLifecycleOwner, Observer<Boolean?> {
            Snackbar.make(requireView(), "BG", Snackbar.LENGTH_LONG).show()
        })

        sendTweetButton.setOnClickListener {
            val massage = massageTweetTextInputEditText.text.toString()
            if (massage.isNotEmpty()) {
                viewModel.createTweet(massage)
            }
        }

    }

    companion object {

        fun newInstance(): Fragment = CreateTweetFragment()

    }

}
