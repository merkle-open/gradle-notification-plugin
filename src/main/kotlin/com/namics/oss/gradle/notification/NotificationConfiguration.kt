package com.namics.oss.gradle.notification

import kotlin.properties.Delegates

/**
 * NotificationConfiguration.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 15:12
 */
class NotificationConfiguration {
    companion object Factory {
        var dir: String = "build/notification_plugin"
            private set
        var prefix: String = "notify_"
            private set
        var postfix: String = ".json"
            private set

        fun initialize(
            dir: String = this.dir,
            prefix: String = this.prefix,
            postfix: String = this.postfix
        ) {
            this.dir = dir
            this.prefix = prefix
            this.postfix = postfix
        }
    }
}