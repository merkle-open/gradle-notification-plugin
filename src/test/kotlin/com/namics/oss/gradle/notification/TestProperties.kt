package com.namics.oss.gradle.notification

import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

/**
 * TestProperties.
 *
 * @author rgsell, Namics AG
 * @since 23.04.20 11:16
 */
class TestProperties(val propertiesFile: String = "test.properties") {
    var inputStream: InputStream?
    val properties = Properties()

    init {
        try {
            inputStream = javaClass.classLoader.getResourceAsStream(propertiesFile) ?: throw IllegalArgumentException(
                "$propertiesFile is required for testing, define testing properties file $propertiesFile")
        } catch (e: FileNotFoundException) {
            throw IllegalArgumentException(
                "$propertiesFile is required for testing, define testing properties file $propertiesFile",
                e
            )
        }
        properties.load(inputStream)
    }

    fun getProperty(key: String): String {
        return properties.getProperty(key)
            ?: throw IllegalArgumentException("property with '$key' not found, define property with '$key' in '$propertiesFile' properties file")
    }
}

fun getTestProperty(key: String, propertiesFile: String = "test.properties"): String {
    return TestProperties(propertiesFile).getProperty(key)
}