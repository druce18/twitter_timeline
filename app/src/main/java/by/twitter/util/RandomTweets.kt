package by.twitter.util

import by.twitter.model.Tweet
import by.twitter.storage.Tweets

class RandomTweets {
    companion object {

        private var flag = true

        fun addTweets() {
            if (flag) {
                for (index in 0..30) {
                    val tweet = randomTweet()
                    Tweets.create(tweet)
                }
                flag = false
            }
        }

        private fun randomTweet(): Tweet {
            val name = randomText((7..14).random())
            val massage = randomText((20..100).random())
            return Tweets.newTweet(name, massage)
        }

        private fun randomText(length: Int): String {
            val allowedChars = " ABCDE FGHIJ KLMNO PQRST UVWXTZ abcde fghik lmnop qrstu vwxyz "
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

    }
}