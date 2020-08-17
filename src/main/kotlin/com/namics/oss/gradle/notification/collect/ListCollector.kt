package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.utils.saveProperty

/**
 * Collect a simple key/value Property and save it as ListProperty.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 11:58
 */
class ListCollector(
    override var propertyKey: String = "listProperty",
    val value: List<String>? = null,
    override var overwrite: Boolean = false
) : Collector {
    override fun collectProperty(): Property {
        return ListProperty(propertyKey, requireNotNull(value))
    }
}

fun listProperty(key: String, value: List<String>, overwrite: Boolean = false): ListCollector {
    return ListCollector(key, value, overwrite)
}