package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.*
import com.namics.oss.gradle.notification.send.*
import org.gradle.internal.impldep.org.eclipse.jgit.api.Git

/**
 * NotificationExtension.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 15:47
 */
open class NotificationExtension(var propertyDir: String = "build/notification_plugin") {
    var propertyPrefix: String = "notify_"
    var propertyPostfix: String = ".json"
    var throwExceptions: Boolean = false
    var notifications: MutableList<Notification> = mutableListOf()

    fun notification(init: Notification.() -> Unit) {
        notifications.add(Notification().apply(init))
    }

    fun gitHistoryCollector(init: GitHistoryCollector.() -> Unit): Collector {
        return GitHistoryCollector().apply(init)
    }

    fun jiraVersionCollector(init: JiraVersionCollector.() -> Unit): Collector {
        return JiraVersionCollector().apply(init)
    }

    fun jsonCollector(init: JsonCollector.() -> Unit): Collector {
        return JsonCollector().apply(init)
    }

    fun listCollector(init: ListCollector.() -> Unit): Collector {
        return ListCollector().apply(init)
    }

    fun listCollector(key: String, value: List<String>, overwrite: Boolean = false): Collector {
        return ListCollector(key, value, overwrite)
    }

    fun stringCollector(init: StringCollector.() -> Unit): Collector {
        return StringCollector().apply(init)
    }

    fun stringCollector(key: String, value: String, overwrite: Boolean = false): Collector {
        return StringCollector(key, value, overwrite)
    }

    fun ticketCollector(init: TicketCollector.() -> Unit): Collector {
        return TicketCollector().apply(init)
    }

    fun chatSender(init: ChatSender.() -> Unit): Sender {
        return ChatSender().apply(init)
    }

    fun consoleSender(init: ConsoleSender.() -> Unit): Sender {
        return ConsoleSender().apply(init)
    }

    fun mailSender(init: MailSender.() -> Unit): Sender {
        return MailSender().apply(init)
    }

    fun newRelicSender(init: NewRelicSender.() -> Unit): Sender {
        return NewRelicSender().apply(init)
    }

    fun slackSender(init: SlackSender.() -> Unit): Sender {
        return SlackSender().apply(init)
    }
}