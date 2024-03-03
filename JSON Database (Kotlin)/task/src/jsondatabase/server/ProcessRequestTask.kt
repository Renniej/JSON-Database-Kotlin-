package jsondatabase.server

import jsondatabase.requestResponse.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Callable


class ProcessRequest(private val socket : Socket, private val dbManager : DatabaseManager) : Callable<Boolean> {

    private val input = DataInputStream(socket.getInputStream())
    private val output = DataOutputStream(socket.getOutputStream())


    override fun call(): Boolean {
        val request = receiveRequest()
        val dbValue : String? = executeRequest(request)
        val response : JsonObject = buildJsonResponse(dbValue, request.type)

        sendResponse(response)

        return request.type == "exit"
    }
    private fun receiveRequest() : Request {
        val request = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!

        println("Received: $request")

        return Json.decodeFromString<Request>(request)
    }

    private fun executeRequest(request : Request) : String? = if (request.type == "exit") "OK" else dbManager.executeCommand(request.type,request.key,request.value)
    private fun buildJsonResponse(dbValue : String?, requestType : String) : JsonObject {

        return buildJsonObject {
            when (dbValue) {
                null -> {
                    put("response", "ERROR")
                    put("reason", "No such key")
                }

                INVALID_CMD -> {
                    put("response", "ERROR")
                    put("reason", "Invalid Command")
                }

                else -> {
                    put("response", "OK")
                    if (requestType == "get")
                        put("value", dbValue)
                }
            }

        }

    }



    private fun sendResponse(response: JsonObject) {
        output.writeUTF(response.toString())
        println("Sent: $response")
    }




}

