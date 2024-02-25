package jsondatabase.client

import jsondatabase.requestResponse.Request
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class ClientManager(val address: String, val port : Int) {

    private val socket : Socket
    private val input : DataInputStream
    private val output : DataOutputStream

    init {
        println("Client started!")

        socket = Socket(InetAddress.getByName(address), port)
        input = DataInputStream(socket.getInputStream())
        output = DataOutputStream(socket.getOutputStream())
    }


    fun sendRequest(request : Request) {
        output.writeUTF(request.toString())
        println("Sent: $request")
    }
    fun receiveResponse() : String {
        val response = input.readUTF()
        println("Received: $response")

        return response
    }

    fun disconnect() {
        //output.writeUTF("exit")
        socket.close()
    }
}