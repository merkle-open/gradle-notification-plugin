package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.collect.Collector
import com.namics.oss.gradle.notification.send.Sender

/**
 * Notification.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 09:05
 */
class Notification {
    var taskName: String = "default"
    var dependsOn: String? = null
    val collectors = mutableListOf<Collector>()
    val senders = mutableListOf<Sender>()

    fun senders(vararg sender: Sender) = senders.addAll(sender)

    fun collectors(vararg collector: Collector) = collectors.addAll(collector)

    fun collect(): Notification {
        collectors.forEach { it.collect() }
        return this
    }

    fun send(): Notification {
        senders.forEach { it.send() }
        return this
    }
}

fun notification(init: Notification.() -> Unit) = Notification().apply(init)