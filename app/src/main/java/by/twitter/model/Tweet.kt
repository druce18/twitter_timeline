package by.twitter.model

data class Tweet(
    val user: User,
    val date: String = "today",
    val massage: String,
    val answers: Int = 0,
    val retweets: Int = 0,
    val likes: Int = 0
) {
    override fun toString(): String {
        return "\n$user: massage = $massage"
    }
}