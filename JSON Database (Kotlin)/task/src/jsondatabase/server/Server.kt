package jsondatabase.server

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket


fun getRecord() {

}

fun main() {

    val address = "127.0.0.1"
    val port = 23456
    val server = ServerSocket(port, 50, InetAddress.getByName(address))
    val socket = server.accept()

    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    println("Server started!")

    val num = input.readUTF().split(" ").find { it.toIntOrNull() != null }
    val response = output.writeUTF("A record # $num was sent!")




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