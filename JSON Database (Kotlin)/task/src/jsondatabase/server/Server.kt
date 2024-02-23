package jsondatabase.server

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket




fun main() {
    println("Server started!")
    val address = "127.0.0.1"
    val port = 23456
    val server = ServerSocket(port, 50, InetAddress.getByName(address))
    val socket = server.accept()

    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())



    val msg = input.readUTF()
    val num = msg.split(" ").find { it.toIntOrNull() != null }

    println("Received: $msg")

    val response = "A record # $num was sent!"
    output.writeUTF(response)

    println("Sent: $response")



    val manager = DatabaseManager( database = JSONDatabase())

    while (true) {
        val input = readln()
        val cmd = input.substringBefore(" ")
        val args = input.substringAfter(" ")

        if (cmd == "exit")
            break
        else
            println(manager.executeCommand(cmd,args))
    }

}