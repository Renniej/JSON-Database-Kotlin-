package jsondatabase.requestResponse

import kotlinx.serialization.Serializable

@Serializable
data class Request(val type : String, val key : String ="", val value  : String = "")