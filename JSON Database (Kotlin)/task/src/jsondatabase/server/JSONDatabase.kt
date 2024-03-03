package jsondatabase.server

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.locks.ReentrantReadWriteLock


const val EMPTY = ""


class JSONDatabase(private val jsonFile : File) : Database<String,String> {

    private val database = Json.decodeFromString<MutableMap<String,String>>(jsonFile.readText())
    override val size: Int
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

    @Synchronized override fun set(key: String, value: String): String? {

        if (key.isBlank()) return null

        database[key] = value
        updateFile()

        return database[key]


    }

    @Synchronized override fun get(key: String) : String? {

        readLock.lock()

        val value = database[key]

        readLock.unlock()

        return value


    }

    @Synchronized override fun delete(key: String): String? {

        val value= database.remove(key)
        updateFile()

        return value
    }



}


