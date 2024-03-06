package jsondatabase.client


import jsondatabase.requestResponse.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File


private val clientDataPath = System.getProperty("user.dir") + File.separator +
        "src" + File.separator +
        "jsondatabase" + File.separator +
        "client" + File.separator +
        "data"


fun createRequestFromFile(fileName : String) : Request {

    val file = File("$clientDataPath${File.separator}$fileName")

    if (!file.exists()) {
        throw Error("$fileName does not exist")
    } else {
        return Json.decodeFromString<Request>(file.readText())
    }

}

fun createRequestFromArgs(args : List<String>): Request {

    val cmdIndex = args.indexOf("-t")
    val keyIndex = args.indexOf("-k")
    val valueIndex = args.indexOf("-v")


    val cmd = args[cmdIndex +1]
    val key = Json.encodeToJsonElement(args[keyIndex +1])
    val value = Json.encodeToJsonElement(args[valueIndex+1])

    return when {  //It is returned this way so that when the request gets serialized parameters that weren't used aren't included in the JSON. Exampple : {"type":"get","key":"1"}  vs {"type":"get","key":"1", value:""}
        valueIndex != -1 -> Request(cmd,key,value)
        keyIndex != -1 -> Request(cmd, key)
        else -> Request(cmd)
    }


}
fun main(arguments : Array<String>) { //args example : -t set -i 148 -m Here is some text to store on the server

    val args = arguments.toList()

    val request : Request = if (args.contains("-in"))
        createRequestFromFile(args[args.indexOf("-in") + 1])
    else
        createRequestFromArgs(args)




    val client = ClientConnection(address = "127.0.0.1", port = 23456)


    client.sendRequest(Json.encodeToString(request))

    client.receiveResponse()
    client.disconnect()

}