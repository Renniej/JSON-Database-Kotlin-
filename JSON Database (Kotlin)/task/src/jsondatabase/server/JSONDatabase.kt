package jsondatabase.server


const val EMPTY = ""

class JSONDatabase(override val size : Int) : Database<String,String> {

    private val database = HashMap<String,String>()

    override fun set(key: String, value: String): Boolean {
        database[key] = value
        return true
    }

    override fun get(key: String) : String? = database[key]

    override fun delete(key: String): Boolean = database.remove(key) != null



}


