package jsondatabase.server

interface Database<KeyType,ValueType> {

    val size: Int

    fun get(key: KeyType): ValueType?

    fun set(key: KeyType, value: ValueType): Boolean

    fun delete(key: KeyType): Boolean


}