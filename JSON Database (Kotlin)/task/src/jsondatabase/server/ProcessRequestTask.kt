package jsondatabase.server

import jsondatabase.requestResponse.Request
import jsondatabase.requestResponse.ServerResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Callable


class ProcessRequest(private val socket : Socket, private val dbManager : DatabaseManager) : Callable<Boolean> {

    private val input = DataInputStream(socket.getInputStream())
    private val output = DataOutputStream(socket.getOutputStream())


    override fun call(): Boolean {
        val request = receiveRequest()

        val response = if (request.type == "exit") {
            ServerResponse.OK
        }
        else {





            dbManager.executeCommand(request.type,request.key,request.value)
        }


        sendResponse(response)

        return request.type == "exit"
    }
    private fun receiveRequest() : Request {
        val request = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!

        println("Received: $request")

        return Json.decodeFromString<Request>(request)
    }

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



    private fun sendResponse(response: ServerResponse) {
        val jsonResponse = Json.encodeToJsonElement(response)

        output.writeUTF(jsonResponse.toString())
        println("Sent: $jsonResponse")
    }




}

