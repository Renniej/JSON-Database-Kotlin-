package jsondatabase.server


const val INVALID_CMD = "Invalid command"
class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : String? {

        val response = when(command) {
            "get" ->  database.get(key)
            "delete" -> database.delete(key)
            "set" -> database.set(key, data)
            else -> INVALID_CMD
        }

       return response
    }


}