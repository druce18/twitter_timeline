package by.twitter.model

data class User(
    val name: String = "name",
    val nameID: String = "@unique_name"
) {
    override fun toString(): String {
        return name
    }
}