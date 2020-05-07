package com.namics.oss.gradle.notification.utils

import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.propertyDir
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.propertyPostfix
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.propertyPrefix
import com.namics.oss.gradle.notification.Property
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File
import java.io.FileNotFoundException

/**
 * Functions for serializing/deserializing properties to and from file.
 *
 * @author rgsell, Namics AG
 * @since 17.04.20 17:33
 */
fun getProperty(propertyKey: String): Property {
    val file = File(propertyDir, "$propertyPrefix$propertyKey$propertyPostfix")
    return getPropertyInternal(file)
}

fun saveProperty(property: Property) {
    val directory = createOrGetDir()
    val file = File(directory, "$propertyPrefix${property.key}$propertyPostfix")
    if (!file.isFile) {
        println("create new file $propertyPrefix${property.key}$propertyPostfix in $propertyDir")
        file.createNewFile()
    }
    val json = Json(JsonConfiguration.Stable)
    file.writeText(json.stringify(Property.serializer(), property))
}

fun isPropertyExisting(propertyKey: String): Boolean{
    val directory = createOrGetDir()
    val file = File(directory, "$propertyPrefix${propertyKey}$propertyPostfix")
    return file.isFile
}

fun getAllProperties(): List<Property> {
    val directory = File(propertyDir)
    return directory.listFiles()?.map { getPropertyInternal(it) }?.toList() ?: return emptyList()
}

fun saveAllProperties(properties: List<Property>) {
    properties.forEach { saveProperty(it) }
}

private fun getPropertyInternal(file: File): Property {
    if (file.isFile) {
        val json = Json(JsonConfiguration.Stable)
        return json.parse(Property.serializer(), file.readText())
    }
    throw FileNotFoundException("property file not found: ${file.name}")
}

private fun createOrGetDir(): File {
    val directory = File(propertyDir)
    if (directory.mkdirs()) {
        println("$directory is created successfully.")
    }
    return directory
}