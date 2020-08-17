package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.saveProperty

/**
 * Collect a simple key/value Property and save it as StringProperty.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 11:56
 */
class StringCollector(override var propertyKey: String = "stringCollector",
                      var value: String? = null,
                      override var overwrite: Boolean = false) :
    Collector {
    override fun collectProperty(): Property {
        return StringProperty(propertyKey, requireNotNull(value))
    }
}

fun property(key: String, value: String, overwrite: Boolean = false): StringCollector {
    return StringCollector(key, value, overwrite)
}