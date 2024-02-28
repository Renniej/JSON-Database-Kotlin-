package jsondatabase.client


fun createRequestFromArgs(args : List<String>): String {


    val cmd = args[args.indexOf("-t") + 1]

    val index = if (args.contains("-i"))
        args[args.indexOf("-i") + 1]
    else
        null

    val data = if (args.contains("-m"))
        args[args.indexOf("-m") + 1]
    else
        null

    return buildString {
        append(cmd)
        index?.let { append(" $index") }
        data?.let { append(" $data") }
    }

}
fun main(args : Array<String>) { //args example : -t set -i 148 -m Here is some text to store on the server


    val request : String = createRequestFromArgs(args.toList())

    val client = ClientConnection(address = "127.0.0.1", port = 23456)


    client.sendRequest(request)
    client.receiveResponse()
    client.disconnect()

}