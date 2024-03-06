package jsondatabase.server

import jsondatabase.requestResponse.ServerResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

import java.util.LinkedList


const val INVALID_CMD = "Invalid command"
class DatabaseManager(private val database : JSONDatabase ) {

    fun executeCommand(command : String, key : JsonElement?, value : JsonElement?) : ServerResponse {

        val keyQueue = if (key as? JsonPrimitive != null) {
            LinkedList<String>().apply {
               add(key.jsonPrimitive.content)
           }
        } else {
            val list = (key as JsonArray).toList().map {it.jsonPrimitive.content}
            LinkedList(list)
        }




        val response = when(command) {

            "delete" -> if (database.delete(keyQueue)) ServerResponse.OK else ServerResponse.ERROR

            "set" ->  {

                if (value == null) {
                    ServerResponse("ERROR", Json.encodeToJsonElement("No value was sent with set request"))
                }
                else {
                    if (database.set(keyQueue, value))
                        ServerResponse.OK
                    else
                        ServerResponse.ERROR
                }


            }


            "get" -> {
                when(val data = database.get(keyQueue)){
                    null -> ServerResponse.ERROR
                    else -> ServerResponse("OK", value = data)
                }
            }

            else -> ServerResponse(INVALID_CMD)
        }

       return response
    }


}