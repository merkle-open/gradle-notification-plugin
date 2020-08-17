package com.namics.oss.gradle.notification.send.slack

import com.namics.oss.gradle.notification.NotificationExtension.Companion.SLACK_DONE
import com.namics.oss.gradle.notification.NotificationExtension.Companion.SLACK_START
import com.namics.oss.gradle.notification.getTestProperty
import com.namics.oss.gradle.notification.model
import com.namics.oss.gradle.notification.send.SlackSender
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * SlackSenderTest.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:56
 */
@Disabled("for manual testing")
internal class SlackSenderTest {
    //configure this variables
    val webhook = getTestProperty("sender.slack.webhook")
    val channel = getTestProperty("sender.slack.channel")

    @Test
    fun sendStart() {
        val model = model {
            property {
                key = "targetEnv"
                value = "DEV"
            }
            property {
                key = "jiraUrl"
                value = "jira.com"
            }
            property {
                key = "version"
                value = "6.7.0-SNAPSHOT"
            }
            property {
                key = "oldVersion"
                value = "6.6.0-SNAPSHOT"
            }
        }

        SlackSender(
            template = SLACK_START,
            webhook = webhook,
            channel = channel
        )
            .send(model)
    }

    @Test
    fun sendStop() {
        val model = model {
            property {
                key = "targetEnv"
                value = "DEV"
            }
            property {
                key = "version"
                value = "6.7.0-SNAPSHOT"
            }
        }

        SlackSender(
            template = SLACK_DONE,
            webhook = webhook,
            channel = channel
        )
            .send(model)
    }
}