package jsondatabase.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

import java.util.LinkedList


const val INVALID_CMD = "Invalid command"
class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : String? {

        val keyQueue = Json.decodeFromString<LinkedList<String>>(key)


        val response = when(command) {
            "get" ->  database.get(keyQueue)
            "delete" -> database.delete(keyQueue)
            "set" -> database.set(keyQueue, data)
            else -> INVALID_CMD
        }

       return response
    }


}