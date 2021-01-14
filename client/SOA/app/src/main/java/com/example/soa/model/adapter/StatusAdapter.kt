package com.example.soa.model.adapter

import com.example.soa.model.Status
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class StatusAdapter : TypeAdapter<Status>() {

    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, value: Status) {
        jsonWriter.value(value.name.toLowerCase())
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): Status {
        when (jsonReader.peek()) {
            JsonToken.STRING -> {
                val value = jsonReader.nextString()
                return Status.values().first { it.label.equals(value, ignoreCase = true) }
            }

            else -> throw RuntimeException("Expected number not " + jsonReader.peek())
        }
    }
}
