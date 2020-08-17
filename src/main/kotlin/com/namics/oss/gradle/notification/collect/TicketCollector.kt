package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.NotificationConfiguration
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.throwExceptions
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import org.gradle.api.logging.Logging
import java.io.FileNotFoundException

/**
 * Collects a ListProperty from an existing ListProperty by searching for a regex pattern.
 *
 * @author rgsell, Namics AG
 * @since 17.04.20 16:10
 */
class TicketCollector(
    override var propertyKey: String = "issues",
    var historyPropertyKey: String = "changes",
    var ticketPattern: String? = null,
    override var overwrite: Boolean = false
) : Collector {
    override fun collectProperty(): Property {
        val logger = Logging.getLogger(this.javaClass)
        try {
            val history = getProperty(historyPropertyKey) as ListProperty
            val regex = Regex(requireNotNull(ticketPattern))
            val tickets =
                history.value.flatMap { regex.findAll(it).map(MatchResult::value).toList() }.distinct().toList()
            return ListProperty(propertyKey, tickets)
        } catch (e: FileNotFoundException) {
            logger.error("File for Property '$historyPropertyKey' not found, Collector for property $historyPropertyKey must be defined before TicketCollector")
            throw e
        }
    }
}