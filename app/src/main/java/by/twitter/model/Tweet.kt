package by.twitter.model

data class Tweet(
    val user: User,
    val date: String,
    val massage: String,
    val answers: Int,
    val retweets: Int,
    val likes: Int
)