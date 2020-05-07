package com.namics.oss.gradle.notification

/**
 * NotificationConfiguration.
 *
 * @author rgsell, Namics AG
 * @since 22.04.20 15:12
 */
class NotificationConfiguration {
    companion object Factory {
        var propertyDir: String = "build/notification_plugin"
            private set
        var propertyPrefix: String = "notify_"
            private set
        var propertyPostfix: String = ".json"
            private set
        var throwExceptions: Boolean = false
            private set

        fun initialize(
                propertyDir: String = this.propertyDir,
                propertyPrefix: String = this.propertyPrefix,
                propertyPostfix: String = this.propertyPostfix,
                throwExceptions: Boolean = this.throwExceptions
        ) {
            this.propertyDir = propertyDir
            this.propertyPrefix = propertyPrefix
            this.propertyPostfix = propertyPostfix
            this.throwExceptions = throwExceptions
        }
    }
}