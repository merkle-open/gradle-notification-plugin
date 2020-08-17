package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.ListCollector
import com.namics.oss.gradle.notification.send.ConsoleSender
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * NotificationDemoPlugin.
 *
 * @author rgsell, Namics AG
 * @since 10.08.20 14:29
 */
class NotificationDemoPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        project.plugins.apply(NotificationPlugin::class.java)
        val extension = project.extensions.getByName("notify") as NotificationExtension
        extension.notify{
            taskName = "notifyTask"
            /* define collectors */
            /* used collectors are StringCollector and ListCollector, more implementations: com.namics.oss.gradle.notification.collect */
            collectors(
                /* function call to create ListCollector, same as ListCollector(key, value)*/
                /* add collector to create "collectors" property */
                com.namics.oss.gradle.notification.collect.listProperty(
                    "collectors",
                    listOf(
                        "GitHistoryCollector",
                        "JsonCollector",
                        "ListCollector",
                        "StringCollector",
                        "TicketCollector",
                        "... or implement your own com.namics.oss.gradle.notification.collect.Collector"
                    )
                ),
                /* add collector to create "senders" property */
                ListCollector(
                    "senders",
                    listOf(
                        "ChatSender",
                        "ConsoleSender",
                        "MailSender",
                        "NewRelicSender",
                        "SlackSender",
                        "... or implement your own com.namics.oss.gradle.notification.send.Sender"
                    )
                ),
                /* function call to create StringCollector, same as StringCollector(key, value) */
                /* add collector to create "message" property */
                com.namics.oss.gradle.notification.collect.property("message", "Hello World!")
            )

            /* define senders, more sender implementations: com.namics.oss.gradle.notification.send */
            senders(
                ConsoleSender("templates/console.mustache")
            )
        }
    }
}