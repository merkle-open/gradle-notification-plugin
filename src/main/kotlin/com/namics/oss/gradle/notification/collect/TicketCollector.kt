package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty

/**
 * TicketExtractor.
 *
 * @author rgsell, Namics AG
 * @since 17.04.20 16:10
 */
class TicketCollector(
    override val propertyKey: String = "issues",
    val historyPropertyKey: String = "changes",
    val ticketPattern: String,
    override val overwrite: Boolean = false
) : Collector {
    override fun collectProperty(): Property {
        val history = getProperty(historyPropertyKey) as ListProperty
        val regex = Regex(ticketPattern)
        val tickets =
            history.value.flatMap { regex.findAll(it).map(MatchResult::value).toList() }.distinct().toList()
        return ListProperty(propertyKey, tickets)
    }
}