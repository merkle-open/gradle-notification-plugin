package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.CHAT_START
import kotlinx.serialization.Serializable
import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.Model

/**
 * Send Google Chat messages.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:24
 */
class ChatSender(
    override var template: String = CHAT_START,
    var webhook: String? = null,
    var threadKey: String = ""
) : Sender {
    override fun sendNotification(model: Model) {
        var text = process(model)
        if (text.length >= 4000) {
            text = text.substring(0, 4000)
        }
        val chatData = getJson(
            ChatData.serializer(),
            ChatData(text)
        )

        val uri = requireNotNull(webhook) + if (!threadKey.isEmpty()) "&threadKey=" + threadKey else ""
        Http().postJson(chatData, uri)
    }
}

@Serializable
data class ChatData(val text: String)