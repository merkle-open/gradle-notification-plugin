package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.content

/**
 * VersionExtractor.
 *
 * @author rgsell, Namics AG
 * @since 08.04.20 17:06
 */
class JsonCollector(
    override val propertyKey: String,
    val uri: String,
    val jsonPath: String = "git.commit.id.full",
    val authHeader: String? = null,
    override val overwrite: Boolean = false
) : Collector {
    override fun collectProperty() : Property {
        val jsonElement = Http(authHeader).getJson(uri)
        val jsonValue = jsonElement.jsonObject.getPath(jsonPath)

        return StringProperty(propertyKey, jsonValue ?: "")
    }
}

fun JsonObject.getPath(jsonPath: String): String? {
    val split = jsonPath.split(delimiters = *arrayOf("."), ignoreCase = false, limit = 2)
    if (split.size == 2) {
        return this.jsonObject[split[0]]?.jsonObject?.getPath(split[1])
    } else {
        return this.jsonObject[split[0]]?.content
    }
}