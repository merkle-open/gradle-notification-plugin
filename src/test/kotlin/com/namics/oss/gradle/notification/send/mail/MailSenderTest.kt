package com.namics.oss.gradle.notification.send.mail

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.MAIL_START
import com.namics.oss.gradle.notification.getTestProperty
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Disabled
import com.namics.oss.gradle.notification.model
import com.namics.oss.gradle.notification.send.MailSender

/**
 * MailSenderTest.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 18:46
 */
internal class MailSenderTest {
    //configure this variables
    @Test
    @Disabled("for manual testing")
    fun send() {
        val model = model {
            property {
                key = "headline"
                value = "Login Deployment"
            }
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
            property {
                key = "issuesUrl"
                value = "jira.com/issues"
            }
            listProperty {
                key = "changes"
                value = listOf("TICKET-123: Test mit bla", "TiCKET-312: Ich habe was Implementiert")
            }
            property {
                key = "version"
                value = "6.7.0-SNAPSHOT"
            }
            property {
                key = "oldVersion"
                value = "6.6.0"
            }
        }


        MailSender(
            template = MAIL_START,
            from = getTestProperty("sender.mail.from"),
            to = getTestProperty("sender.mail.to"),
            subject = "Deployment",
            smtpHost = getTestProperty("sender.mail.smtpHost")
        ).send(model)
    }
}