package by.twitter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.twitter.model.TweetsSingleton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_tweet.view.*
import kotlinx.android.synthetic.main.top_bar.view.*

class CreateTweetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_create_tweet, container, false)
        view.nameMenuTextView.text = getString(R.string.new_tweet)

        view.sendTweetButton.setOnClickListener {
            Log.i(CreateTweetFragment.toString(), "Run send tweet ")
            val username = view.usernameTweetTextInputEditText.text.toString()
            val massage = view.massageTweetTextInputEditText.text.toString()
            if (username.isNotEmpty() && massage.isNotEmpty()) {
                TweetsSingleton.createTweet(username, massage)
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this)
                    .replace(R.id.mainContent, NewMainFragment.newInstance())
                    .commit()
            } else {
                Snackbar.make(view, R.string.wrong_input, Snackbar.LENGTH_LONG).show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): Fragment = CreateTweetFragment()
    }

}
