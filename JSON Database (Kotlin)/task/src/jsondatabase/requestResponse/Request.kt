package jsondatabase.requestResponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class Request(val type : String, val key : JsonElement? = null, val value  : JsonElement? = null)

