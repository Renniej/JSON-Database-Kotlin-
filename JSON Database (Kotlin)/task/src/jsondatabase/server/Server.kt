package jsondatabase.server


fun main() {
    val manager = DatabaseManager( database = JSONDatabase())

    while (true) {
        val input = readln()
        val cmd = input.substringBefore(" ")
        val args = input.substringAfter(" ")

        if (cmd == "exit")
            break
        else
            println(manager.executeCommand(cmd,args))
    }

}