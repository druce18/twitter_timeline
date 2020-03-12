package by.twitter.oldClasses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.twitter.R
import by.twitter.model.Tweet
import by.twitter.model.User
import kotlinx.android.synthetic.main.layout_tweet.*

class OldMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User("Andrey Bereznyatsky", "@druce")
        val tweet = Tweet(
            user = user,
            massage = "My name is Andrey. Im 23 years-old. Now I learn kotlin. Its very difficult!"
        )

        userNameTweetTextView.text = tweet.user.name
        userIDTweetTextView.text = tweet.user.nameID
        massageTweetTextView.text = tweet.massage
        answersCountTweetTextView.text = tweet.answers.toString()
        retweetsCountTweetTextView.text = tweet.retweets.toString()
        likesCountTweetTextView.text = tweet.likes.toString()

    }

}
