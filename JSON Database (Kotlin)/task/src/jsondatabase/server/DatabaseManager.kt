package jsondatabase.server

class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, arguments : String = "") : String {

        val args = arguments.split(" ")

        val key = args.first()
        val data = args.subList(1, args.size).joinToString(" ")

        val response = when(command) {
            "get" ->  database.get(key) ?: "ERROR"
            "delete" -> if (database.delete(key)) "OK" else "ERROR"
            "set" -> if (database.set(key, data)) "OK" else "ERROR"
            else -> "Invalid command"
        }

       return response
    }


}