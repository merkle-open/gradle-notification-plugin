package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.utils.isPropertyExisting
import com.namics.oss.gradle.notification.utils.saveProperty

/**
 * Collector.
 *
 * @author rgsell, Namics AG
 * @since 30.03.20 16:30
 */
interface Collector {
    val propertyKey: String
    val overwrite: Boolean
    fun collectProperty() : Property
    fun collect() {
        if (overwrite) {
            saveProperty(collectProperty())
        }
        if(!overwrite && !isPropertyExisting(propertyKey)){
            saveProperty(collectProperty())
        }
    }
}