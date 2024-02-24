package jsondatabase.server

import jsondatabase.requestResponse.Request
import jsondatabase.requestResponse.Response
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket



class JSONDatabaseServer(val address : String, val port : Int, database : JSONDatabase) {

    private val server = ServerSocket(port, 50, InetAddress.getByName(address))
    private val dbManager =  DatabaseManager(database)

    private val socket : Socket = server.accept()
    private var input : DataInputStream = DataInputStream(socket.getInputStream())
    private var output : DataOutputStream = DataOutputStream(socket.getOutputStream())

    init {
        println("Server started!")
    }



    fun receiveRequest() : Request {
        val data = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!
        val request =  Request.of(data)

        println("Received: ${request.cmd} ${request.args}")

        return request
    }

    fun executeRequest(request : Request) : Response {
       val status = when(request.cmd) {
           "exit" ->  "OK"
           else -> dbManager.executeCommand(request.cmd, request.args)
       }

        return Response(message = status)
    }

    fun sendResponse(response: Response) {
        output.writeUTF(response.message)
        println("Sent: ${response.message}")
    }

    fun exit(){
        socket.close()
        server.close()
    }
}

