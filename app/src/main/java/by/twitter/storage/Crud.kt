package by.twitter.storage

import java.util.ArrayList

interface Crud<E> {

    fun create(element: E)

    fun read(): List<E>

    fun update(element: E, index: Int)

    fun delete(index: Int)

}