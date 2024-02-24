package jsondatabase.server

import jsondatabase.requestResponse.Response
import kotlin.system.exitProcess

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)



fun main() {
    val db = JSONDatabase(1000)
    val server = JSONDatabaseServer(address = "127.0.0.1", port = 23456,  database = db)

    val request = server.receiveRequest()
    val response : Response

    println( "REQUEST : $request")
    response = if (request.cmd == "exit") {
    Response("OK")
    }
    else {
        server.executeRequest(request)

    }

    server.sendResponse(response)

}