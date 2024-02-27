package jsondatabase.server


const val EMPTY = ""

class JSONDatabase (override val size : Int) : Database<String> {

    private val database = Array(size = size) {""}

    private fun isKeyInDatabaseBounds(key :  Int) = (key in 0 until size)

    override fun set(key : Int, value : String) : Boolean {

        return try  {
            database[key] = value
            true
        } catch (e : IndexOutOfBoundsException) {
            false
        }

    }

    override fun get(key : Int) : String? {
        return try {
            database[key].ifEmpty { null }
        } catch (e : IndexOutOfBoundsException) {
            null
        }
    }


    override fun delete(key : Int) : Boolean {
        return try {
            database[key] = ""
            true
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }


}


