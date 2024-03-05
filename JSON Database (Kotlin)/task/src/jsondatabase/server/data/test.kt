package jsondatabase.server.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

fun main() {

    val jsonStr = "{\n" +
            "      \"name\":\"Elon Musk\",\n" +
            "      \"car\":{\n" +
            "         \"model\":\"Tesla Roadster\",\n" +
            "         \"year\":\"2018\"\n" +
            "      },\n" +
            "      \"rocket\":{\n" +
            "         \"name\":\"Falcon 9\",\n" +
            "         \"launches\":\"88\"\n" +
            "      }\n" +
            "   }"



    val json = Json.parseToJsonElement(jsonStr)

    println(json)
}