package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.Collector
import com.namics.oss.gradle.notification.send.Sender
import org.gradle.api.Project

/**
 * NotificationExtension.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 15:47
 */
public open class NotificationExtension {
    var collectors: List<Collector> = emptyList()
    var senders: List<Sender> = emptyList()
}