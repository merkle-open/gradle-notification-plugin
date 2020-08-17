package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.tasks.NotifyTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/**
 * com.namics.oss.gradle.notification.NotificationPlugin.
 *
 * example: see registred "notifyTask" which collects properties with StringCollector and ListCollector and send it to ConsoleSender.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 11:48
 */
class NotificationPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = project.extensions.create(
            "notify",
            NotificationExtension::class.java,
            project.buildDir.absolutePath + "/notify"
        )
        NotificationConfiguration.initialize(
            propertyDir = extension.propertyDir,
            propertyPrefix = extension.propertyPrefix,
            propertyPostfix = extension.propertyPostfix,
            throwExceptions = extension.throwExceptions
        )
        extension.notifications.forEach {
            project.tasks.register(it.taskName, NotifyTask::class) {
                notification = it
            }
        }
    }
}