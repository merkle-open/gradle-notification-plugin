package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.utils.saveProperty

/**
 * ListCollector.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 11:58
 */
class ListCollector(
    override val propertyKey: String,
    val value: List<String>,
    override val overwrite: Boolean = false
) : Collector {
    override fun collectProperty(): Property {
        return ListProperty(propertyKey, value)
    }
}

fun listProperty(key: String, value: List<String>, overwrite: Boolean = false): ListCollector {
    return ListCollector(key, value, overwrite)
}