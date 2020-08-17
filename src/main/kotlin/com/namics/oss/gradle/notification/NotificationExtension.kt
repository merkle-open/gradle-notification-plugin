package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.*
import com.namics.oss.gradle.notification.send.*
import com.namics.oss.gradle.notification.tasks.NotifyTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/**
 * NotificationExtension.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 15:47
 */
open class NotificationExtension(val project: Project, var propertyDir: String = "build/notification_plugin") {
    val CHAT_START = DefaultTemplates.CHAT_START
    val CHAT_DONE = DefaultTemplates.CHAT_DONE
    val SLACK_START = DefaultTemplates.SLACK_START
    val SLACK_DONE = DefaultTemplates.SLACK_DONE
    val MAIL_START = DefaultTemplates.MAIL_START
    val MAIL_DONE = DefaultTemplates.MAIL_DONE
    val NEW_RELIC_CHANGELOG = DefaultTemplates.NEW_RELIC_CHANGELOG
    val NEW_RELIC_DESCRIPTION = DefaultTemplates.NEW_RELIC_DESCRIPTION

    var propertyPrefix: String = "notify_"
    var propertyPostfix: String = ".json"
    var throwExceptions: Boolean = false

    fun notification(init: Notification.() -> Unit) {
        val notification = Notification().apply(init)
        project.tasks.register(notification.taskName, NotifyTask::class) {
            this.notification = notification
            if (notification.dependsOn != null) {
                dependsOn(notification.dependsOn)
            }
        }
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