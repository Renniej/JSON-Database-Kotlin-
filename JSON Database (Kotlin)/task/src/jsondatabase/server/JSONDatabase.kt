package jsondatabase.server

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File
import java.util.concurrent.locks.ReentrantReadWriteLock



//https://stackoverflow.com/questions/25402149/how-to-modify-an-existing-jsonobject-in-java


class JSONDatabase() {

    private val database = mutableMapOf<String,Any>()

    private fun getPointViaKey(keys : List<String>, lastPoint : MutableMap<String, Any>) : Any? {

        if (keys.isEmpty()) return lastPoint

        val nextPoint = lastPoint[keys.first()]

        return when(nextPoint) {
            is String ->  nextPoint
            is MutableMap<*, *> ->  getPointViaKey(keys.subList(1,keys.lastIndex), nextPoint as MutableMap<String, Any> )
            else -> null
        }

    }


    fun get(keys : List<String>) : JsonElement? {



        var curPoint =  getPointViaKey(keys,database)


        return curJson

    }


    fun delete (keys: List<String>, values : String) : JsonElement? {

        val jsonExist = get(keys) != null



        val json = buildJsonObject {

        }



    }

}



/*
class JSONDatabase(private val jsonFile : File)  {

    private val database = Json.decodeFromString<MutableMap<String,Any>>(jsonFile.readText())
     val size: Int
        get() {
          return  database.size
        }

    private val fileAccessLock = ReentrantReadWriteLock()
    private val readLock = fileAccessLock.readLock()
    private val writeLock = fileAccessLock.writeLock()

    private fun updateFile() {
        writeLock.lock()
            jsonFile.writeText(Json.encodeToString(database))
        writeLock.unlock()
    }

    fun updateEntry(keys : List<String>, json : JsonObject? = null) {

        var entry : JsonObject

        if (json == null) {



            entry = database[keys.first()]!!
        }








    }



    @Synchronized fun set(keys: List<String>, value: String): JsonObject? {

        var entry : JsonObject?

        if (keys.isEmpty())
            entry = null
        else if (keys.size == 1) {
            database[keys.first()] = value
            entry = database[keys.first()]
        }
        else {

            val newData = value.toMutableMap()

            if (!database.containsKey(keys.first())) {
                database[keys.first()] = buildJsonObject { }
            }


            val oldData = database[keys.first()]!!.toMutableMap()




        }


        updateFile()

        return entry

    }

    @Synchronized fun get(key: List<String>) : String? {

        readLock.lock()

        val value = database[key]

        readLock.unlock()

        return value


    }

    @Synchronized fun delete(key: List<String>): String? {

        val value= database.remove(key)
        updateFile()

        return value
    }



}
*/


