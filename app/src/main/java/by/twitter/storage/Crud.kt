package by.twitter.storage

interface Crud<E> {

    fun create(element: E)

    fun read(): List<E>

    fun update(element: E, index: Int)

    fun delete(index: Int)

}