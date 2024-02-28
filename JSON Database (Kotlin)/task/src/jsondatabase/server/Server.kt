package jsondatabase.server

import jsondatabase.requestResponse.Request

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)



fun main() {

    val db = JSONDatabase(1000)
    val server = JSONDatabaseServer(address = "127.0.0.1", port = 23456,  database = db)


   while(true) {

       val clientRequest : Request = server.receiveRequest()
       val response : String = server.executeRequest(clientRequest)

       server.sendResponse(response)

       if (clientRequest.type != "exit" )
           server.resetConnection()
       else
           break


   }


    server.exit()

}