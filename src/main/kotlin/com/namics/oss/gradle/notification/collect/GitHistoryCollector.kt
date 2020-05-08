package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.GitUtils
import org.gradle.api.logging.Logging
import com.namics.oss.gradle.notification.utils.getProperty
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
    val newRevision: String = "HEAD",
    val oldRevisionPropertyKey: String = "oldRevision",
    var rootPath: String,
    val limit: Int = 100,
    override val overwrite: Boolean = false
) : Collector {
    val logger = Logging.getLogger(this.javaClass)
    override fun collectProperty(): Property {
        try {
            val oldRevision = getProperty(oldRevisionPropertyKey) as StringProperty
            if (oldRevision.value.isEmpty()) {
                logger.error("Property '$oldRevisionPropertyKey' has no value, $oldRevisionPropertyKey input is not correct")
            }
            val git = GitUtils(
                rootPath = File(rootPath),
                logger = Logging.getLogger(this.javaClass)
            )
            var gitLog = git.log(newRevision, oldRevision.value, limit).toList()
            if (gitLog.isEmpty()) {
                gitLog = listOf("-")
            }

            return ListProperty(propertyKey, gitLog)
        } catch (e: FileNotFoundException){
            logger.error("File for Property '$oldRevisionPropertyKey' not found, Collector for property $oldRevisionPropertyKey must be defined before GitHistoryExtractor")
            throw e
        } catch (e: GradleException){
            logger.error("Could not extract from git")
            throw e
        }
    }
}