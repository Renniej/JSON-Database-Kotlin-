package jsondatabase.server

import jsondatabase.requestResponse.Request
import jsondatabase.requestResponse.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket



class JSONDatabaseServer(val address : String, val port : Int, database : JSONDatabase) {

    private val server = ServerSocket(port, 50, InetAddress.getByName(address))
    private val dbManager =  DatabaseManager(database)

    //all initialized in openConnection() fuc
    private lateinit var socket : Socket
    private lateinit var input : DataInputStream
    private lateinit var output : DataOutputStream

    init {
        println("Server started!")
        resetConnection()
    }


    fun resetConnection() {
          socket  = server.accept()
          input = DataInputStream(socket.getInputStream())
          output  = DataOutputStream(socket.getOutputStream())
    }


    fun receiveRequest() : Request {
        val request = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!

        println("Received: $request")

        return Json.decodeFromString<Request>(request)
    }

    fun executeRequest(request : Request) : String {

        val response =  if (request.type == "exit"){
            Response("OK")
        }else {

        }









           dbManager.executeCommand(request.type,request.key,request.value)
        }

        return response
    }

    fun sendResponse(response: String) {
        output.writeUTF(response)
        println("Sent: $response")
    }


    fun exit(){
        server.close()
    }
}

