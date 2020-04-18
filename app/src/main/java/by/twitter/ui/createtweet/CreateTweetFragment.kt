package by.twitter.ui.createtweet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.twitter.R
import by.twitter.ui.main.MainFragment
import kotlinx.android.synthetic.main.fragment_create_tweet.*
import kotlinx.android.synthetic.main.tool_bar.*

class CreateTweetFragment : Fragment(R.layout.fragment_create_tweet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backImageButton.visibility = View.VISIBLE
        nameMenuTextView.text = getString(R.string.new_tweet)
        massageTweetInputEditText.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(massageTweetInputEditText, InputMethodManager.SHOW_IMPLICIT)

        backImageButton.setOnClickListener {
            navigateToTimeline()
        }

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
            if (it == null) return@Observer
            if (it) {
                navigateToTimeline()
            }
        })

        sendTweetButton.setOnClickListener {
            val massage = massageTweetInputEditText.text.toString()
            if (massage.isNotEmpty()) {
                viewModel.createTweet(massage)
            }
        }

    }

    private fun navigateToTimeline() {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                        R.id.nav_controller,
                        MainFragment.newInstance()
                )
                .commit()
    }

    companion object {

        fun newInstance(): Fragment = CreateTweetFragment()

    }

}
