package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.ListCollector
import com.namics.oss.gradle.notification.collect.StringCollector
import com.namics.oss.gradle.notification.collect.listProperty
import com.namics.oss.gradle.notification.collect.property
import com.namics.oss.gradle.notification.send.ConsoleSender
import com.namics.oss.gradle.notification.tasks.NotifyTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/**
 * com.namics.oss.gradle.notification.NotificationPlugin.
 *
 * example: see registred "notifyTask" which collects properties with StringCollector and ListCollector and send it to ConsoleSender.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 11:48
 */
class NotificationPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = project.extensions.create("notify", NotificationExtension::class.java)
        NotificationConfiguration.initialize(dir = project.buildDir.absolutePath + "/notify")
        tasks.register("notifyTask", NotifyTask::class) {
            /* define collectors */
            /* used collectors are StringCollector and ListCollector, more implementations: com.namics.oss.gradle.notification.collect */
            collectors(
                /* function call to create ListCollector, same as ListCollector(key, value)*/
                /* add collector to create "collectors" property */
                listProperty(
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
                property("message", "Hello World!")
            )

            /* define senders, more sender implementations: com.namics.oss.gradle.notification.send */
            senders(
                ConsoleSender("templates/console.mustache")
            )

        }
    }
}