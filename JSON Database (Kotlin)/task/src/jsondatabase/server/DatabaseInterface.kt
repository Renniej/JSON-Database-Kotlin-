package jsondatabase.server

interface Database<T> {

    val size: Int

    fun get(key: Int): T?

    fun set(key: Int, value: T): Boolean

    fun delete(key: Int): Boolean


}