package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.throwExceptions
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.utils.isPropertyExisting
import com.namics.oss.gradle.notification.utils.saveProperty
import org.gradle.api.logging.Logging

/**
 * Collector.
 *
 * @author rgsell, Namics AG
 * @since 30.03.20 16:30
 */
interface Collector {
    val propertyKey: String
    val overwrite: Boolean

    /**
     * Collector specific implementation.
     */
    fun collectProperty(): Property

    /**
     * Collect and save property
     */
    fun collect() {
        try {
            if (overwrite) {
                saveProperty(collectProperty())
            }
            if (!overwrite && !isPropertyExisting(propertyKey)) {
                saveProperty(collectProperty())
            }
        } catch (e: Exception) {
            Logging.getLogger(this.javaClass).error("Something went wrong while collecting", e)
            if (throwExceptions) {
                throw e
            }
        }

    }
}