package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.getPathString

/**
 * Collect a StringProperty from a json endpoint, traversed by json path.
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
        val jsonValue = jsonElement.jsonObject.getPathString(jsonPath)

        return StringProperty(propertyKey, jsonValue ?: "")
    }
}