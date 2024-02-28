package jsondatabase.client

import jsondatabase.requestResponse.Request
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



fun createRequestFromArgs(args : List<String>): Request {

    val cmdIndex = args.indexOf("-t")
    val keyIndex = args.indexOf("-k")
    val valueIndex = args.indexOf("-v")

    val cmd = args[cmdIndex +1]
    val key = args[keyIndex +1]
    val value = args[valueIndex+1]

    return when {  //It is returned this way so that when the request gets serialized parameters that weren't used aren't included in the JSON. Exampple : {"type":"get","key":"1"}  vs {"type":"get","key":"1", value:""}
        valueIndex != -1 -> Request(cmd,key,value)
        keyIndex != -1 -> Request(cmd, key)
        else -> Request(cmd)
    }


}
fun main(args : Array<String>) { //args example : -t set -i 148 -m Here is some text to store on the server


    val request : Request = createRequestFromArgs(args.toList())

    val client = ClientConnection(address = "127.0.0.1", port = 23456)


    client.sendRequest(Json.encodeToString(request))

    client.receiveResponse()
    client.disconnect()

}