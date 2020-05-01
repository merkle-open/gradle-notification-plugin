package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.utils.GitUtils
import org.gradle.api.logging.Logging
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import java.io.File
import kotlin.streams.toList

class GitHistoryCollector(
    override val propertyKey: String = "changes",
    val version: String = "HEAD",
    val versionPropertyKey: String = "oldVersion",
    var rootPath: String,
    val limit: Int = 100,
    override val overwrite: Boolean = false
) : Collector {
    override fun collectProperty(): Property {
        val oldVersion = getProperty(versionPropertyKey) as StringProperty
        if (oldVersion.value.isEmpty()) {
            throw IllegalArgumentException("Property '$versionPropertyKey' not found in Context, $versionPropertyKey Property or Extractor must be defined before GitHistoryExtractor")
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
    }
}