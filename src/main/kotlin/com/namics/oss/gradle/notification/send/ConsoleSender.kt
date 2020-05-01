package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.Model

/**
 * Send message to console.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 12:08
 */
class ConsoleSender(override val template: String = "templates/console.mustache") : Sender {
    override fun send(model: Model) {
        println(process(model))
    }
}