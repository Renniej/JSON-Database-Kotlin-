package jsondatabase.server

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)



fun main() {
    val server = JSONDatabaseServer(address = "127.0.0.1", port = 23456)
    server.start()
}