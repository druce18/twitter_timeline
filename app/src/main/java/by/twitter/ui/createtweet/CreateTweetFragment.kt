package by.twitter.ui.createtweet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.twitter.R
import by.twitter.TwitterApplication
import by.twitter.ui.main.MainFragment
import kotlinx.android.synthetic.main.fragment_create_tweet.*
import kotlinx.android.synthetic.main.tool_bar.*
import javax.inject.Inject

class CreateTweetFragment : Fragment(R.layout.fragment_create_tweet) {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var createTweetViewModel: CreateTweetViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTweetViewModel = ViewModelProvider(this, viewModelProviderFactory).get(CreateTweetViewModel::class.java)

        nameMenuTextView.text = getString(R.string.new_tweet)
        massageTweetInputEditText.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(massageTweetInputEditText, InputMethodManager.SHOW_IMPLICIT)

        backImageButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        subscribeViewModel()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TwitterApplication).appComponent.inject(this)
    }

    private fun subscribeViewModel() {
        createTweetViewModel.loading.observe(viewLifecycleOwner, Observer<Boolean?> {
            if (it == null) return@Observer
            if (it) {
                progressBar.visibility = View.VISIBLE
                tweetFormMaterialCardView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tweetFormMaterialCardView.visibility = View.VISIBLE
            }
        })

        createTweetViewModel.goBackToTimeline.observe(viewLifecycleOwner, Observer<Boolean?> {
            if (it == null) return@Observer
            if (it) {
                requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                                R.id.nav_controller,
                                MainFragment.newInstance()
                        )
                        .addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        })

        sendTweetButton.setOnClickListener {
            val massage = massageTweetInputEditText.text.toString()
            if (massage.isNotEmpty()) {
                createTweetViewModel.createTweet(massage)
            }
        }

    }

    companion object {

        fun newInstance(): Fragment = CreateTweetFragment()

    }

}
