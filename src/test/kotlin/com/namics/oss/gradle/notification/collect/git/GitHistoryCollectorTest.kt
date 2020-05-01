package com.namics.oss.gradle.notification.collect.git

import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.collect.GitHistoryCollector
import com.namics.oss.gradle.notification.getTestProperty
import com.namics.oss.gradle.notification.property
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * GitHistoryCollectorTest.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 13:15
 */
@Disabled("for manual testing")
internal class GitHistoryCollectorTest {

    //change this variables
    val rootPath = getTestProperty("collector.git.rootpath")

    @Test
    fun extract() {
        saveProperty(
            property {
                key = "oldVersion"
                value = "adcf0cda5147edf7d66152686c4f955c7dd17b90"
            })
        GitHistoryCollector(
            propertyKey = "changes",
            rootPath = rootPath,
            overwrite = true
        ).collect()
        val history = getProperty("changes") as ListProperty
        history.value.forEach(::println)
    }
}