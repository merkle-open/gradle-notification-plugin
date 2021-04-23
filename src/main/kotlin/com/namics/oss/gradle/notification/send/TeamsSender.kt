package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.TEAMS_START
import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Model
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * Send Microsoft Teams Messages.
 *
 * @author rgsell, Namics AG
 * @since 23.04.21 17:24
 */
class TeamsSender(
    override var template: String = TEAMS_START,
    var webhook: String? = null,
    var type: String = "message",
    var contentType: String = "application/vnd.microsoft.card.adaptive"
) : Sender {
    override fun sendNotification(model: Model) {
        val processedJson = process(model)

        val json = Json(JsonConfiguration.Stable)
        val content = json.parseJson(processedJson)
        val teamsData = getJson(
            TeamsData.serializer(),
            TeamsData(
                type = type,
                attachments = listOf(
                    TeamsAttachment(
                        contentType = contentType,
                        content = content
                    )
                )
            )
        )

        Http().postJson(teamsData, requireNotNull(webhook))
    }
}

@Serializable
data class TeamsData(
    val attachments: List<TeamsAttachment>,
    val type: String
)

@Serializable
data class TeamsAttachment(
    val content: JsonElement,
    val contentType: String
)