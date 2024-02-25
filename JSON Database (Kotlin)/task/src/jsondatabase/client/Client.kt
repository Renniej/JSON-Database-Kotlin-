package jsondatabase.client

import jsondatabase.requestResponse.Request
import java.util.*


fun createRequestFromArgs(args : List<String>): Request {


    val cmd = args[args.indexOf("-t") + 1]
    val index = args[args.indexOf("-i") + 1]

    println("CMD : $cmd ,${cmd.length}")

   return when(cmd) {
        "exit" -> Request.of(cmd)
        "set" -> {
            val otherData = args[args.indexOf("-m") + 1]


            Request.of("$cmd $index $otherData")
        }
        else -> Request.of("$cmd $index")
    }

}
fun main(args : Array<String>) { //args example : -t set -i 148 -m Here is some text to store on the server


    val request = createRequestFromArgs(args.toList())
    val client = ClientManager(address = "127.0.0.1", port = 23456)



    client.sendRequest(request)
    client.receiveResponse()
    client.disconnect()

}