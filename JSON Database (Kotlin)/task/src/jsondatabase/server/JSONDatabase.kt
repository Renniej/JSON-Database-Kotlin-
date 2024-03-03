package jsondatabase.server

import kotlinx.serialization.Serializable
import java.util.concurrent.locks.ReentrantReadWriteLock


const val EMPTY = ""


class JSONDatabase() : Database<String,String> {

    private val database = HashMap<String,String>()
    override val size: Int
        get() = database.size

    private val rwLock = ReentrantReadWriteLock()
    @Synchronized override fun set(key: String, value: String): String? {

        if (key.isBlank()) return null

        rwLock.writeLock().lock()

        database[key] = value

        rwLock.writeLock().unlock()

        return database[key]


    }

    @Synchronized override fun get(key: String) : String? {

        rwLock.readLock().lock()

        val value = database[key]

        rwLock.readLock().unlock()

        return value


    }

    @Synchronized  override fun delete(key: String): String? {
        rwLock.writeLock().lock()

       val value= database.remove(key)

        rwLock.writeLock().unlock()

        return value
    }



}


