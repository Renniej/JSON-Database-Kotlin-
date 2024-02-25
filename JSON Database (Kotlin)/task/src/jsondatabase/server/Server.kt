package jsondatabase.server

import jsondatabase.requestResponse.Request
import jsondatabase.requestResponse.Response
import kotlin.system.exitProcess

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)


fun processOneRequest(server : JSONDatabaseServer) : Request {
    val request = server.receiveRequest()
    val response = server.executeRequest(request)
    server.sendResponse(response)

    return request
}

fun main() {
    val db = JSONDatabase(1000)
    val server = JSONDatabaseServer(address = "127.0.0.1", port = 23456,  database = db)


    while (true) {
        val handledRequest = processOneRequest(server)

        if (handledRequest.cmd == "exit")
            break;
        else
            server.closeConnect()

    }


    server.exit()

}