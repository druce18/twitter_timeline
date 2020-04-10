package by.twitter.storage

interface Crud<E> {

    fun create(text: String)

    fun read(): List<E>

    fun updateAll()

    fun delete(id: Long)

}