package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.Model
import com.github.mustachejava.DefaultMustacheFactory
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.throwExceptions
import com.namics.oss.gradle.notification.utils.getAllProperties
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.gradle.api.logging.Logging
import java.io.StringWriter

/**
 * Sender.
 *
 * @author rgsell, Namics AG
 * @since 30.03.20 14:56
 */
interface Sender {
    val template: String

    /**
     * Sender specific implementation.
     */
    fun sendNotification(model: Model)

    /**
     * Send notification with custom context (model)
     */
    fun send(model: Model) {
        try {
            sendNotification(model)
        } catch (e: Exception) {
            Logging.getLogger(this.javaClass).error("Something went wrong while sending", e)
            if (throwExceptions) {
                throw e
            }
        }
    }

    /**
     * Send notification with all properties as context
     */
    fun send() {
        val model = Model()
        model.addAllProperties(getAllProperties())
        send(model)
    }

    fun process(model: Model): String {
        val mf = DefaultMustacheFactory()
        val mustache = mf.compile(this.template)
        val payload = StringWriter()
        mustache.execute(payload, model.content)
        return payload.toString()
    }

    fun process(template: String, model: Model): String {
        val mf = DefaultMustacheFactory()
        val mustache = mf.compile(template)
        val payload = StringWriter()
        mustache.execute(payload, model.content)
        return payload.toString()
    }

    fun <T> getJson(serializer: SerializationStrategy<T>, value: T): String {
        val json = Json(JsonConfiguration.Stable)
        val jsonData = json.stringify(serializer, value)
        return jsonData
    }
}