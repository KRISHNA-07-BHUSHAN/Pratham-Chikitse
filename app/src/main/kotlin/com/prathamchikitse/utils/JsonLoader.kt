package com.prathamchikitse.utils

import android.content.Context
import org.json.JSONObject

object JsonLoader {

    fun loadJson(context: Context, fileName: String): JSONObject {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)

            inputStream.read(buffer)
            inputStream.close()

            JSONObject(String(buffer, Charsets.UTF_8))
        } catch (e: Exception) {
            e.printStackTrace()
            JSONObject()
        }
    }
}