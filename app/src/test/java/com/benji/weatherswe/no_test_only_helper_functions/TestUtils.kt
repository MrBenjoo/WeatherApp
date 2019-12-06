package com.benji.weatherswe.no_test_only_helper_functions

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object TestUtils {

    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }

    fun loadJsonFromResources(jsonFileName: String): String {
        val stream = this.javaClass.classLoader!!.getResourceAsStream(jsonFileName)
        return convertStreamToString(stream)
    }

}