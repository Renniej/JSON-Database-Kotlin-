package jsondatabase.server

import kotlinx.serialization.Serializable


const val EMPTY = ""


class JSONDatabase(override val size : Int) : Database<String,String> {

    private val database = HashMap<String,String>()

    override fun set(key: String, value: String): String? {
        database[key] = value
        return database[key]
    }

    override fun get(key: String) : String? = database[key]

    override fun delete(key: String): String? = database.remove(key)



}


