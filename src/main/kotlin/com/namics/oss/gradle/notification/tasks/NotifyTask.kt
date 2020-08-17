package com.namics.oss.gradle.notification.tasks

import com.namics.oss.gradle.notification.Notification
import com.namics.oss.gradle.notification.collect.Collector
import com.namics.oss.gradle.notification.send.Sender
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Task definition for demonstration purposes.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 16:03
 */
open class NotifyTask : DefaultTask() {
    @Input
    var notification = Notification()

    @TaskAction
    fun send() {
        /* collect all collectors and send to defined senders */
        notification.collect().send()
    }

    fun collectors(vararg collectors: Collector){
        notification.collectors(*collectors)
    }

    fun senders(vararg sender: Sender) {
        notification.senders(*sender)
    }
}