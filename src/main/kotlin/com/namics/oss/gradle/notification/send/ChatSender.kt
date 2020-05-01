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
class ChatSender(override val template: String = CHAT_START, val webhook: String, val threadKey: String = "") : Sender {
    override fun send(model: Model) {
        var text = process(model)
        if(text.length>= 4000){
            text = text.substring(0,4000)
        }
        val chatData = getJson(
            ChatData.serializer(),
            ChatData(text)
        )

        val uri = webhook + if (!threadKey.isEmpty()) "&threadKey=" + threadKey else ""
        Http().postJson(chatData, uri)
    }
}

@Serializable
data class ChatData(val text: String)