package jsondatabase.client

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

fun main() {
    val address = "127.0.0.1"
    val port = 23456
    val socket = Socket(InetAddress.getByName(address), port)
    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())



    output.writeUTF("Give me a record # 12")

}