package jsondatabase.requestResponse

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import java.security.cert.CertPathValidatorException.Reason

@Serializable
data class ServerResponse(val response : String, val value : JsonElement? = null, val reason : String? = null) {
    companion object {
        val OK = ServerResponse(response =  "OK")
        val ERROR = ServerResponse(response = "ERROR", reason = "No such key")
    }
}

