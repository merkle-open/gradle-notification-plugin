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
    override var template: String = NEW_RELIC_DESCRIPTION,
    var changelogTemplate: String = NEW_RELIC_CHANGELOG,
    var webhook: String? = null,
    var apiKey: String? = null,
    var revision: String? = null,
    var user: String? = null
) : Sender {
    override fun sendNotification(model: Model) {
        val description = process(template, model)
        val changelog = process(changelogTemplate, model)
        val newRelicData = getJson(
            NewRelicData.serializer(),
            NewRelicData(Deployment(requireNotNull(revision), changelog, requireNotNull(user), description))
        )

        val headers = mapOf("X-Api-Key" to requireNotNull(apiKey))
        Http().postJson(newRelicData, requireNotNull(webhook), headers)
    }
}

@Serializable
data class NewRelicData(val deployment: Deployment)

@Serializable
data class Deployment(val revision: String, val changelog: String, val user: String, val description: String)