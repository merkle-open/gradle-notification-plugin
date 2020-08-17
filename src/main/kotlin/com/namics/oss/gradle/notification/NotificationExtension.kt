package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.*
import com.namics.oss.gradle.notification.send.*

/**
 * NotificationExtension.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 15:47
 */
open class NotificationExtension(var propertyDir: String = "build/notification_plugin") {
    companion object {
        const val CHAT_START = "templates/chat-start.mustache"
        const val CHAT_DONE = "templates/chat-done.mustache"
        const val SLACK_START ="templates/slack-start.mustache"
        const val SLACK_DONE = "templates/slack-done.mustache"
        const val MAIL_START = "templates/mail-start.mustache"
        const val MAIL_DONE = "templates/mail-done.mustache"
        const val NEW_RELIC_CHANGELOG ="templates/newrelic-changelog.mustache"
        const val NEW_RELIC_DESCRIPTION = "templates/newrelic-description.mustache"
    }
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

    fun basicAuthHeader(username: String, password: String): String {
        return createBasicAuthHeader(username, password)
    }
}