package jsondatabase.server


const val EMPTY = ""

class JSONDatabase : Database<String> {

    private val database = Array(size = 100) {""}
    override val size = database.size

    //returns true if the key is within the bounds of the Database else returns false
    private fun isInRange(key :  Int) = (key in 0 until size)

    //returns true if the key is not withing the bounds of the Database else returns false
    private fun isNotInRange(key : Int) = !isInRange(key)

    //returns true if the key exist within the database else false
    private fun hasKey(key : Int) : Boolean  = isInRange(key)

    //returns true if the key is empty or does not exist in the database else false
    override fun isEmptyKey(key : Int) = hasKey(key) && database[key] == EMPTY


    //sets index in the database to the string sent.  Returns true if the value was added/overwritten sucessfully and false otherwise
    override fun set(key : Int, value : String) : Boolean {
        return when(hasKey(key)){
            true -> {
                database[key] = value
                true
            }
            false -> false
        }

    }

    //returns the value stored at the key. if no value exist at that key then it returns null
    override fun get(key : Int) : String? {

        return if (isEmptyKey(key)) null
        else
            database[key]

    }

    //deletes entry at index and returns true if the deletion was successful else false
    override fun delete(key : Int) : Boolean {
        return if (!hasKey(key)) {
            false
        } else {
            database[key] = ""
            true
        }
    }


}


