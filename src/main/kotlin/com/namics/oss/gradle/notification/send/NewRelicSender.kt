package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.NEW_RELIC_CHANGELOG
import com.namics.oss.gradle.notification.DefaultTemplates.Companion.NEW_RELIC_DESCRIPTION
import kotlinx.serialization.Serializable
import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Model

/**
 * Create Deploments in new relic.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:26
 */
class NewRelicSender(
    override val template: String = NEW_RELIC_DESCRIPTION,
    val changelogTemplate: String = NEW_RELIC_CHANGELOG,
    val webhook: String,
    val apiKey: String,
    val revision: String,
    val user: String
) : Sender {
    override fun sendNotification(model: Model) {
        val description = process(template, model)
        val changelog = process(changelogTemplate, model)
        val newRelicData = getJson(
            NewRelicData.serializer(),
            NewRelicData(Deployment(revision, changelog, user, description))
        )

        val headers = mapOf("X-Api-Key" to apiKey)
        Http().postJson(newRelicData, webhook, headers)
    }
}

@Serializable
data class NewRelicData(val deployment: Deployment)

@Serializable
data class Deployment(val revision: String, val changelog: String, val user: String, val description: String)