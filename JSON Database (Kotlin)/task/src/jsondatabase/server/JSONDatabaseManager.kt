package jsondatabase.server

import jsondatabase.requestResponse.ServerResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

import java.util.LinkedList


const val INVALID_CMD = "Invalid command"
class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : String = "", data : String = "") : ServerResponse {


        val keyJson = Json.encodeToJsonElement(key)



        val keyQueue = if (keyJson as? JsonPrimitive != null) {
            LinkedList<String>().apply {
               add(keyJson.toString())
           }
        } else {
            val list = (keyJson as JsonArray).toList().map {it.toString()}
            LinkedList(list)
        }




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