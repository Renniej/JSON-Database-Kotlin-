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
    fun set(keys : Queue<String> , value: JsonElement) : Boolean {


        database = modifyJsonTree(keys,database,value)
        updateFile()

        return true
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
        updateFile()
        return true
    }



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
            val next = curElement.toMap()[keyQueue.remove()]
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

}



