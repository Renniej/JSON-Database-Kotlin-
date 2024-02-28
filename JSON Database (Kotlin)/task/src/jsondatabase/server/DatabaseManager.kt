package jsondatabase.server

class DatabaseManager(private val database : Database<String>) {


    fun executeCommand(command : String, arguments : String = "0") : String {

        val args = arguments.split(" ")
        val index = args.first().toInt() -1  //if user enters 1 we want to retrieve index 0 and etc
        val value = args.subList(1, args.size).joinToString(" ")

        val response = when(command) {
            "get" ->  database.get(index) ?: "ERROR"
            "delete" -> if (database.delete(index)) "OK" else "ERROR"
            "set" -> if (database.set(index, value)) "OK" else "ERROR"
            else -> "Invalid command"
        }

       return response

    }



}