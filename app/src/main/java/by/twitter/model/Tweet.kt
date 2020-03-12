package by.twitter.model

import java.util.*

data class Tweet(
    val user: User,
    val date: Date = Date(),
    val massage: String,
    val answers: Int = 0,
    val retweets: Int = 0,
    val likes: Int = 0
) {
    override fun toString(): String {
        return "\n$user: massage = $massage"
    }
}