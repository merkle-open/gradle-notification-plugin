package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.CHAT_DONE
import com.namics.oss.gradle.notification.collect.*
import com.namics.oss.gradle.notification.send.ChatSender
import com.namics.oss.gradle.notification.send.ConsoleSender
import com.namics.oss.gradle.notification.send.MailSender
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

/**
 * NotificationTest.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 09:33
 */
@Disabled("for manual testing")
internal class NotificationTest : AbstractMockHttpMockServer() {

    @Test
    fun sendConsole() {
        notification {
            collectors(
                StringCollector("message", "Hello World!"),
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
                listProperty(
                    "senders",
                    listOf(
                        "ChatSender",
                        "ConsoleSender",
                        "MailSender",
                        "NewRelicSender",
                        "SlackSender",
                        "... or implement your own com.namics.oss.gradle.notification.send.Sender"
                    )
                )
            )
            senders(
                ConsoleSender("templates/console.mustache")
            )
        }.collect().send()
    }

    @Test
    fun sendChat() {
        createMockServer()
        notification {
            collectors(
                property("targetEnv", "DEV"),
                property("gitUrl", "https://github.com/namics/gradle-notification-plugin"),
                property("jiraUrl", "jira.com"),
                property("version", "0.1.0"),
                property("branch", "master"),
                property("revision", "123123123123123123123"),
                property("environmentUrl", "https://github.com/namics/gradle-notification-plugin"),
                JsonCollector(
                    propertyKey = "oldRevision",
                    uri = protocol + host + ":" + port + endpoint,
                    jsonPath = "git.commit.id.full",
                    authHeader = createBasicAuthHeader("info", "password")
                ),
                JsonCollector(
                    propertyKey = "oldVersion",
                    uri = protocol + host + ":" + port + endpoint,
                    jsonPath = "build.version",
                    authHeader = createBasicAuthHeader("info", "password")
                ),
                GitHistoryCollector(
                    propertyKey = "changes",
                    rootPath = getTestProperty("collector.git.rootpath")
                )
            )
            senders(
                ChatSender(
                    webhook = getTestProperty("sender.chat.webhook"),
                    threadKey = "12"
                )
            )
        }.collect().send()
    }

    @Test
    fun sendMail() {
        createMockServer()
        notification {
            collectors(
                property("headline", "Deployment"),
                property("targetEnv", "DEV"),
                property("gitUrl", "https://github.com/namics/gradle-notification-plugin"),
                property("jiraUrl", "jira.com"),
                property("version", "0.1.0"),
                property("branch", "develop"),
                property("customText", "\r\nCustom Text\r\n"),
                property("environmentUrl", "https://github.com/namics/gradle-notification-plugin"),
                JsonCollector(
                    propertyKey = "oldRevision",
                    uri = protocol + host + ":" + port + endpoint,
                    jsonPath = "git.commit.id.full",
                    authHeader = createBasicAuthHeader("info", "password")
                ),
                JsonCollector(
                    propertyKey = "oldVersion",
                    uri = protocol + host + ":" + port + endpoint,
                    jsonPath = "build.version",
                    authHeader = createBasicAuthHeader("info", "password")
                ),
                GitHistoryCollector(
                    propertyKey = "changes",
                    rootPath = getTestProperty("collector.git.rootpath")
                )
            )
            senders(
                MailSender(
                    from = getTestProperty("sender.mail.from"),
                    to = getTestProperty("sender.mail.to"),
                    subject = "Deployment",
                    smtpHost = getTestProperty("sender.mail.smtpHost")
                ),
                ChatSender(
                    webhook = getTestProperty("sender.chat.webhook"),
                    threadKey = "12"
                ),
                ChatSender(
                    template = CHAT_DONE,
                    webhook = getTestProperty("sender.chat.webhook"),
                    threadKey = "12"
                )
            )
        }.collect().send()
    }

    fun createMockServer() {
        MockServerClient(host, Integer.valueOf(port)).`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath(endpoint)
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(
                    """{
                            "git": {
                                "commit": {
                                    "id": { 
                                        "full": "e8e249ae15ea4b0d75e262c79933107a9d534452" 
                                    }
                                }
                            },
                            "build": {
                                "version": "8.4.0-SNAPSHOT"
                            }
                        }"""
                )
        )
    }
}