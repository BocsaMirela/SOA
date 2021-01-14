package com.example.soa.model.adapter

import com.example.soa.util.Constants.FORMAT
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : TypeAdapter<Date>() {

    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, value: Date?) {
        jsonWriter.value(value?.let { SimpleDateFormat(FORMAT, Locale.US).format(it) })
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): Date? {
        return when (jsonReader.peek()) {
            JsonToken.STRING -> jsonReader.nextString().takeIf { !it.isNullOrBlank() }?.let { SimpleDateFormat(FORMAT, Locale.US).parse(it) }
            JsonToken.NUMBER -> Date(jsonReader.nextLong())
            else -> throw RuntimeException("Expected number not " + jsonReader.peek())
        }
    }
}
