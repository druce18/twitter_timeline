package by.twitter.model

data class User(
    val name: String,
    val nameID: String = "@druce18"
) {
    override fun toString(): String {
        return name
    }
}