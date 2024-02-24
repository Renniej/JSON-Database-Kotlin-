package jsondatabase.client

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

fun main(args : Array<String>) {



    println("Client started!")
    val address = "127.0.0.1"
    val port = 23456
    val socket = Socket(InetAddress.getByName(address), port)
    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    val msg = "Give me a record # 12"
    output.writeUTF(msg)
    println("Sent: $msg")

    val response = input.readUTF()
    println("Received: $response")


}