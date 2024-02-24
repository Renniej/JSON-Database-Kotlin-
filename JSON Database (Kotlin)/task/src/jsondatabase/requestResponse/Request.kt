package jsondatabase.requestResponse

data class Request(val cmd : String, val args : String) {
    companion object{
         fun of(data : String)  : Request {

            val argList = data.split(" ")

            val cmd = argList[0] //set, get or delete
             val args = data.substringAfter("$cmd ","")

            return Request(cmd, args)

        }
    }

    override fun toString(): String {
        return when {
            args.isBlank() -> cmd
            else-> "$cmd $args"
        }
    }



}

