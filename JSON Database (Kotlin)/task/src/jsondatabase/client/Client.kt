package jsondatabase.client

import jsondatabase.requestResponse.Request


fun createRequestFromArgs(args : Array<String>): Request {

    val cmd = args[args.indexOf("-t") + 1]
    val index = args[args.indexOf("-i") + 1]

   return when(cmd) {
        "exit" -> Request.of(cmd)
        "set" -> {


            val otherData = args.toString().substringAfter("-m ")



            Request.of("$cmd $index $otherData")
        }
        else -> Request.of("$cmd $index")
    }
}
fun main(args : Array<String>) { //args example : -t set -i 148 -m Here is some text to store on the server


    val request = createRequestFromArgs(args)
    val client = ClientManager(address = "127.0.0.1", port = 23456)



    client.sendRequest(request)
    client.receiveResponse()
    client.disconnect()

}