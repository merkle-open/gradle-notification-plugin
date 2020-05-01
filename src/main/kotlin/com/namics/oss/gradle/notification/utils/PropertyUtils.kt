package com.namics.oss.gradle.notification.utils

import com.namics.oss.gradle.notification.NotificationConfiguration
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.dir
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.postfix
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.prefix
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
    val file = File(dir, "$prefix$propertyKey$postfix")
    return getPropertyInternal(file)
}

fun saveProperty(property: Property) {
    val directory = createOrGetDir()
    val file = File(directory, "$prefix${property.key}$postfix")
    if (!file.isFile) {
        println("create new file $prefix${property.key}$postfix in $dir")
        file.createNewFile()
    }
    val json = Json(JsonConfiguration.Stable)
    file.writeText(json.stringify(Property.serializer(), property))
}

fun isPropertyExisting(propertyKey: String): Boolean{
    val directory = createOrGetDir()
    val file = File(directory, "$prefix${propertyKey}$postfix")
    return file.isFile
}

fun getAllProperties(): List<Property> {
    val directory = File(dir)
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
    val directory = File(dir)
    if (directory.mkdirs()) {
        println("$directory is created successfully.")
    }
    return directory
}