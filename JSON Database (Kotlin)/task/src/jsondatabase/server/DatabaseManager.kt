package jsondatabase.server

class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : <Boolean,Value> {

        val response = when(command) {
            "get" ->  database.get(key) ?: "ERROR"
            "delete" -> if (database.delete(key)) "OK" else "ERROR"
            "set" -> if (database.set(key, data)) "OK" else "ERROR"
            else -> "Invalid command"
        }

       return response
    }


}