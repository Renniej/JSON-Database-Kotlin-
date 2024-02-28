package jsondatabase.server

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




    fun receiveRequest() : String {
        val request = input.readUTF()  //receive message from client.  example : get 1   set 58 Hello World!

        println("Received: $request")

        return request
    }






    fun executeRequest(request : String) : String {

        val cmd = request.split(" ")[0]
        val args = request.substringAfter("$cmd ")


        val response =  when(cmd) {
            "exit" ->  "OK"
            else -> dbManager.executeCommand(cmd, args)
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

