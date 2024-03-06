package jsondatabase.server

import jsondatabase.requestResponse.ServerResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

import java.util.LinkedList


const val INVALID_CMD = "Invalid command"
class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : ServerResponse {

        val keyQueue = Json.decodeFromString<LinkedList<String>>(key)


        val response = when(command) {

            "delete" -> if (database.delete(keyQueue)) ServerResponse.OK else ServerResponse.ERROR
            "set" -> if (database.set(keyQueue, data)) ServerResponse.OK else ServerResponse.ERROR
            "get" -> {
                when(val value = database.get(keyQueue)){
                    null -> ServerResponse.ERROR
                    else -> ServerResponse("OK", value = value)
                }
            }

            else -> ServerResponse(INVALID_CMD)
        }

       return response
    }


}