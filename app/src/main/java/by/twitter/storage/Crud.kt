package by.twitter.storage

interface Crud<E> {

    fun create(text: String)

    fun read(): List<E>

    fun update()

    fun delete(id: Long)

}