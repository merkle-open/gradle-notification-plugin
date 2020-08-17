package com.namics.oss.gradle.notification.send

import kotlinx.serialization.Serializable
import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Model
import com.namics.oss.gradle.notification.NotificationExtension.Companion.SLACK_START

/**
 * Send Slack Messages.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:24
 */
class SlackSender(
    override var template: String = SLACK_START,
    var webhook: String? = null,
    var channel: String? = null
) : Sender {

    override fun sendNotification(model: Model) {
        val text = process(model)
        val slackData = getJson(
            SlackData.serializer(),
            SlackData(requireNotNull(channel), text)
        )

        Http().postJson(slackData, requireNotNull(webhook))
    }
}

@Serializable
data class SlackData(val channel: String, val text: String)