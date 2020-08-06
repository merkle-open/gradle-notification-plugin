package com.namics.oss.gradle.notification.utils

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.content

/**
 * JsonUtils.
 *
 * @author rgsell, Namics AG
 * @since 05.08.20 17:16
 */

fun JsonObject.getPathString(jsonPath: String): String? {
    return this.getPathElement(jsonPath)?.content
}

fun JsonObject.getPathElement(jsonPath: String): JsonElement? {
    val split = jsonPath.split(delimiters = *arrayOf("."), ignoreCase = false, limit = 2)
    if (split.size == 2) {
        return this.jsonObject[split[0]]?.jsonObject?.getPathElement(split[1])
    } else {
        return this.jsonObject[split[0]]
    }
}