package com.namics.oss.gradle.notification.send.slack

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.TEAMS_DONE
import com.namics.oss.gradle.notification.DefaultTemplates.Companion.TEAMS_START
import com.namics.oss.gradle.notification.getTestProperty
import com.namics.oss.gradle.notification.model
import com.namics.oss.gradle.notification.send.TeamsSender
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * TeamsSenderTest.
 *
 * @author rgsell, Namics AG
 * @since 23.04.21 17:56
 */
@Disabled("for manual testing")
internal class TeamsSenderTest {
    //configure this variables
    val webhook = getTestProperty("sender.teams.webhook")

    @Test
    fun sendStart() {
        val model = model{
            property {
                key = "targetEnv"
                value = "DEV"
            }
            property {
                key = "gitUrl"
                value = "github.com"
            }
            property {
                key = "jiraUrl"
                value = "jira.com"
            }
            listProperty {
                key = "changes"
                value = listOf("TICKET-123: Test mit bla", "TICKET-312: Ich habe was Implementiert")
            }
            property {
                key = "branch"
                value = "develop"
            }
        }

        TeamsSender(
            template = TEAMS_START,
            webhook = webhook
        ).send(model)
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
                value = "1.0-SNAPSHOT"
            }
        }

        TeamsSender(
            template = TEAMS_DONE,
            webhook = webhook
        ).send(model)
    }
}