package jsondatabase.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.locks.ReentrantReadWriteLock


//https://stackoverflow.com/questions/25402149/how-to-modify-an-existing-jsonobject-in-java



class JSONDatabase(private val jsonFile : File) {

    private var database = Json.decodeFromString<MutableMap<String,JsonElement>>(jsonFile.readText())

    private val fileAccessLock = ReentrantReadWriteLock()
    private val readLock = fileAccessLock.readLock()
    private val writeLock = fileAccessLock.writeLock()
    @Synchronized
    private fun updateFile() {
        writeLock.lock()
        jsonFile.writeText(Json.encodeToString(database))
        writeLock.unlock()
    }
    @Synchronized
    private fun iterateDownJSONObject(keyQueue : Queue<String>, curElement: JsonElement?) : JsonElement? {

        if (curElement == null) return null //base case 1
        if (keyQueue.isEmpty()) return curElement //base case 2

        return if (curElement is JsonObject) {
            val next = curElement[keyQueue.remove()]
            iterateDownJSONObject(keyQueue,next)
        }
        else { // We can only iterate down a JSONObject. if it isn't a JSON Object and there are still keys to look for then we can assume the key doesn't exist :)
            null
        }

    }

    @Synchronized
    //will also add the folder/node if it doesn't exist. //if value is = null we delete the entry
    private fun modifyJsonTree(keyQueue: Queue<String>, curMap : MutableMap<String,JsonElement>, value : JsonElement, shouldDelete : Boolean = false) : MutableMap<String,JsonElement> {

        return when(keyQueue.size) {
            0 -> throw Exception("NO KEYS WERE SENT?!??!?")
            1 ->  {
                val key = keyQueue.first()

                if (shouldDelete) {
                    curMap.remove(key)
                } else { //add value
                    curMap[key] = value
                }

                curMap
            }
            else -> {

                val curKey = keyQueue.remove()

                if (!curMap.containsKey(curKey)) {
                    curMap[curKey] = buildJsonObject { } //if the key does not exist in the table then create it :)
                }


                if (curMap[curKey] !is JsonObject) {
                    throw Exception("Asking to find a key in non-JSON Object")
                } else {
                    val jsonObj = curMap[curKey] as JsonObject
                    val nextMap = jsonObj.toMutableMap()

                    curMap[curKey] = Json.encodeToJsonElement(modifyJsonTree(keyQueue,nextMap,value,shouldDelete))

                    curMap
                }

            }
        }




    }

    @Synchronized
    fun set(keys : Queue<String> , value: String) : JsonElement? {

        val firstKey = keys.first()
        val jsonValue = Json.encodeToJsonElement(value)

        database = modifyJsonTree(keys,database,jsonValue)

        return database[firstKey]
    }
    @Synchronized
    fun get(keys : Queue<String>) : JsonElement? {


        val jsonElement = database[keys.remove()]

        return iterateDownJSONObject(keys,jsonElement)
    }

    @Synchronized
    fun delete (keys: Queue<String>) : Boolean {
        //Json.encode here is unnecessary but im tired of working on this stage ): <
        database = modifyJsonTree(keys,database,  Json.encodeToJsonElement(""), shouldDelete = true)
        return true
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


