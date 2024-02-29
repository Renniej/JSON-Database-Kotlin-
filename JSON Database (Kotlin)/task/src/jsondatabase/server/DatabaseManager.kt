package jsondatabase.server

class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : String? {

        val response = when(command) {
            "get" ->  database.get(key)
            "delete" -> database.delete(key)
            "set" -> database.set(key, data)
            else -> "Invalid command"
        }

       return response
    }


}