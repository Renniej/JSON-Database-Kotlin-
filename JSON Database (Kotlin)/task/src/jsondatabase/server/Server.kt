package jsondatabase.server

import jsondatabase.requestResponse.Request
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.net.InetAddress
import java.net.ServerSocket
import java.util.concurrent.*

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)

const val NUM_OF_THREADS = 4
const val ADDRESS = "127.0.0.1"
const val PORT = 23456
fun main() {

    val threadManager = Executors.newFixedThreadPool(NUM_OF_THREADS)

    val dbManager = DatabaseManager(JSONDatabase())
    val serverSocket = ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))
    var shutdownServer = false;

    println("Server started!")

    while(!shutdownServer) {

        val client = serverSocket.accept()
        val requestTask = ProcessRequest(client,dbManager)

        val exitServer : Future<Boolean> = threadManager.submit(requestTask)

        threadManager.execute {

            try {
                if (exitServer.get(5, TimeUnit.SECONDS) == true) {
                    threadManager.shutdown()
                    serverSocket.close()
                    shutdownServer = true
                }
            } catch (e : TimeoutException) {
                println(e.message)
            }
        }

    }



}