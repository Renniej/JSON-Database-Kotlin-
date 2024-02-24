package jsondatabase.server

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class JSONDatabaseServer(val address : String, val port : Int) {

    private val server = ServerSocket(port, 50, InetAddress.getByName(address))
    private val dbManager =  DatabaseManager(JSONDatabase())

    fun start() {
        println("Server started!")

        val socket =  server.accept()
        val input = DataInputStream(socket.getInputStream())
        val output = DataOutputStream(socket.getOutputStream())

        val request = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!
        println("Received: $request")

        val (cmd,args) = configureRequest(request)  //translate message into a format that can be sent to our manager
        val response : String = dbManager.executeCommand(cmd,args) //execute request

        output.writeUTF(response) //send status of request to client :)
        println("Sent: $response")
    }

    private fun configureRequest(msg : String)  : List<String> {

        val args = msg.split(" ")
        val cmd = args[0] //set, get or delete

        return listOf(cmd, msg.substringAfter(cmd))
    }


}

