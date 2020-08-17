package com.namics.oss.gradle.notification.send.newrelic

import com.namics.oss.gradle.notification.NotificationExtension.Companion.NEW_RELIC_CHANGELOG
import com.namics.oss.gradle.notification.NotificationExtension.Companion.NEW_RELIC_DESCRIPTION
import com.namics.oss.gradle.notification.getTestProperty
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Disabled
import com.namics.oss.gradle.notification.model
import com.namics.oss.gradle.notification.send.NewRelicSender

/**
 * NewRelicSenderTest.
 *
 * @author rgsell, Namics AG
 * @since 08.04.20 14:58
 */
@Disabled("for manual testing")
internal class NewRelicSenderTest {

    @Test
    fun send() {
        val model = model {
            property {
                key = "targetEnv"
                value = "DEV"
            }
            property {
                key = "jiraUrl"
                value =
                    "https://jira.com/issues/?jql=key%20in%20(TICKET-3408,TICKET-3410,TICKET-3541,TICKET-3615,TICKET-3623) "
            }
            property {
                key = "version"
                value = "8.2.0"
            }
            property {
                key = "revision"
                value = "2a45bdc6daf4898b9b28fcb9e6075dbf6827b772"
            }
            listProperty {
                key = "issues"
                value = listOf(
                    "https://jira.com/browse/TICKET-3408",
                    "https://jira.com/browse/TICKET-3410",
                    "https://jira.com/browse/TICKET-3541"
                )
            }
        }

        NewRelicSender(
            template = NEW_RELIC_DESCRIPTION,
            changelogTemplate = NEW_RELIC_CHANGELOG,
            webhook = getTestProperty("sender.newrelic.webhook"),
            apiKey = getTestProperty("sender.newrelic.apikey"),
            revision = getTestProperty("sender.newrelic.revision"),
            user = getTestProperty("sender.newrelic.user")
        ).send(model)
    }
}