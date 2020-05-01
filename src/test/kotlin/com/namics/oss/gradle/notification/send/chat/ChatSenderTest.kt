package com.namics.oss.gradle.notification.send.chat

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.CHAT_DONE
import com.namics.oss.gradle.notification.DefaultTemplates.Companion.CHAT_START
import com.namics.oss.gradle.notification.getTestProperty
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import com.namics.oss.gradle.notification.model
import com.namics.oss.gradle.notification.send.ChatSender

/**
 * ChatSenderTest.
 *
 * @author rgsell, Namics AG
 * @since 30.03.20 14:57
 */
@Disabled("for manual testing")
internal class ChatSenderTest {
    //configure this variables
    val webhook = getTestProperty("sender.chat.webhook")

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
        }

        ChatSender(template = CHAT_START, webhook = webhook)
            .send(model)
    }

    @Test
    fun sendDone() {
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

        ChatSender(template = CHAT_DONE, webhook = webhook)
            .send(model)
    }


}