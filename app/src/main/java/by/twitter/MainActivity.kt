package by.twitter

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.twitter.model.Tweet
import by.twitter.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var tweetUserNameTextView: TextView
    private lateinit var tweetUserIDTextView: TextView
    private lateinit var tweetTimeTextView: TextView
    private lateinit var tweetMassageTextView: TextView
    private lateinit var tweetAnswersTextView: TextView
    private lateinit var tweetRetweetsTextView: TextView
    private lateinit var tweetLikesTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.nav_view)
        tweetUserNameTextView = findViewById(R.id.tweet_user_name)
        tweetUserIDTextView = findViewById(R.id.tweet_user_id)
        tweetTimeTextView = findViewById(R.id.tweet_time)
        tweetMassageTextView = findViewById(R.id.tweet_massage)
        tweetAnswersTextView = findViewById(R.id.tweet_answers_count)
        tweetRetweetsTextView = findViewById(R.id.tweet_retweets_count)
        tweetLikesTextView = findViewById(R.id.tweet_likes_count)

        val user = User("Andrey Bereznyatsky", "@druce")
        val tweet = Tweet(
            user,
            "12 min",
            "My name is Andrey. Im 23 years-old. Now I learn kotlin. Its very difficult!",
            5,
            7,
            9
        )

        tweetUserNameTextView.text = tweet.user.name
        tweetUserIDTextView.text = tweet.user.nameID
        tweetTimeTextView.text = tweet.date
        tweetMassageTextView.text = tweet.massage
        tweetAnswersTextView.text = tweet.answers.toString()
        tweetRetweetsTextView.text = tweet.retweets.toString()
        tweetLikesTextView.text = tweet.likes.toString()

    }

}
