package jsondatabase.server

interface Database<KeyType,ValueType> {

    val size: Int

    fun get(key: KeyType): ValueType?

    fun set(key: KeyType, value: ValueType): String?

    fun delete(key: KeyType): String?


}