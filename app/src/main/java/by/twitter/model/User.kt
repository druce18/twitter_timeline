package by.twitter.model

data class User(
    val name: String,
    val nameID: String = "userID"
) {
    override fun toString(): String {
        return name
    }
}