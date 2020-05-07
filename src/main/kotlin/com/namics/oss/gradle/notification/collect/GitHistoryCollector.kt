package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.NotificationConfiguration
import com.namics.oss.gradle.notification.NotificationConfiguration.Factory.throwExceptions
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.GitUtils
import org.gradle.api.logging.Logging
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import org.gradle.api.GradleException
import java.io.File
import java.io.FileNotFoundException
import kotlin.streams.toList

/**
 * Collect git log from native git client and store it as ListProperty.
 *
 * @author rgsell, Namics AG
 * @since 08.04.20 17:06
 */
class GitHistoryCollector(
    override val propertyKey: String = "changes",
    val version: String = "HEAD",
    val versionPropertyKey: String = "oldVersion",
    var rootPath: String,
    val limit: Int = 100,
    override val overwrite: Boolean = false
) : Collector {
    val logger = Logging.getLogger(this.javaClass)
    override fun collectProperty(): Property {
        try {
            val oldVersion = getProperty(versionPropertyKey) as StringProperty
            if (oldVersion.value.isEmpty()) {
                logger.error("Property '$versionPropertyKey' has no value, $versionPropertyKey input is not correct")
            }
            val git = GitUtils(
                rootPath = File(rootPath),
                logger = Logging.getLogger(this.javaClass)
            )
            var gitLog = git.log(version, oldVersion.value, limit).toList()
            if (gitLog.isEmpty()) {
                gitLog = listOf("-")
            }

            return ListProperty(propertyKey, gitLog)
        } catch (e: FileNotFoundException){
            logger.error("File for Property '$versionPropertyKey' not found, Collector for property $versionPropertyKey must be defined before GitHistoryExtractor")
            throw e
        } catch (e: GradleException){
            logger.error("Could not extract from git")
            throw e
        }
    }
}