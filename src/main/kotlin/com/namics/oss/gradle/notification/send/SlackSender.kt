package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.SLACK_START
import kotlinx.serialization.Serializable
import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Model

/**
 * Send Slack Messages.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:24
 */
class SlackSender(override val template: String = SLACK_START, val webhook: String, val channel: String) : Sender {

    override fun sendNotification(model: Model) {
        val text = process(model)
        val slackData = getJson(
            SlackData.serializer(),
            SlackData(channel, text)
        )

        Http().postJson(slackData, webhook)
    }
}

@Serializable
data class SlackData(val channel: String, val text: String)