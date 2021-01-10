package com.example.soa.managers

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

interface IResourceManager {
    fun getString(stringId: Int): String
    fun getString(stringId: Int, vararg formatArgs: Any): String
    fun getQuantityString(stringId: Int, quantity: Int): String
    fun getAsset(assetId: Int): String
}

class ResourceManager(private val context: Context) : IResourceManager {

    override fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

    override fun getString(stringId: Int, vararg formatArgs: Any): String {
        return context.getString(stringId, *formatArgs)
    }

    override fun getQuantityString(stringId: Int, quantity: Int): String {
        return context.resources.getQuantityString(stringId, quantity, quantity)
    }

    override fun getAsset(assetId: Int): String {
        return getContentOfInputStream(context.resources.openRawResource(assetId))
    }

    private fun getContentOfInputStream(inputStream: InputStream): String {
        var reader: BufferedReader? = null
        val content = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(inputStream))
            var mLine: String? = reader.readLine()
            while (mLine != null) {
                content.append(mLine)
                mLine = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return content.toString()
    }
}
