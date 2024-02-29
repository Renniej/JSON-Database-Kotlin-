package jsondatabase.server

import jsondatabase.requestResponse.Request
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

//parses message into list of containing the command and associated data. (Function mainly used to get proper signature for DatabaseManager execute function)



fun main() {

    val db = JSONDatabase(1000)
    val server = JSONDatabaseServer(address = "127.0.0.1", port = 23456,  database = db)


   while(true) {

       val clientRequest : Request = server.receiveRequest()
       val dbValue : String? = server.executeRequest(clientRequest) //returns null if request fails

       val response = buildJsonObject {

           when (dbValue) {

               null -> {
                   put("response", "ERROR")
                   put("reason", "No such key")
               }

               INVALID_CMD -> {
                   put("response", "ERROR")
                   put("reason", "Invalid Command")
               }

               else -> {
                   put("response", "OK")
                   if (clientRequest.type == "get")
                       put("value", dbValue)
               }

           }

       }


       server.sendResponse(response.toString())

       if (clientRequest.type != "exit" )
           server.resetConnection()
       else
           break


   }


    server.exit()

}