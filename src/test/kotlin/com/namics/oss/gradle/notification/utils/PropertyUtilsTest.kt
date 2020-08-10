package com.namics.oss.gradle.notification.utils

import com.namics.oss.gradle.notification.*
import com.namics.oss.gradle.notification.Property.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * PropertyUtilsTest.
 *
 * @author rgsell, Namics AG
 * @since 20.04.20 08:55
 */
internal class PropertyUtilsTest {

    @Test
    fun stringProperty() {
        saveProperty(property {
            key = "test"
            value = "testValue"
        })

        val property = getProperty("test") as StringProperty
        assertEquals("test", property.key)
        assertEquals("testValue", property.value)
    }

    @Test
    fun listProperty() {
        saveProperty(listProperty {
            key = "testList"
            value = listOf("testValue1", "testValue2")
        })

        val property = getProperty("testList") as ListProperty

        assertEquals("testList", property.key)
        assertEquals(listOf("testValue1", "testValue2"), property.value)
    }

    @Test
    fun jiraVersionProperty() {
        saveProperty(jiraVersionProperty {
            key = "testJiraVersion"
            value = mapOf(
                "Bug" to listOf(
                    mapOf(
                        "issueKey" to "KEY-1",
                        "summary" to "this is the summary for first issue",
                        "type" to "Bug"
                    )
                ),
                "Story" to listOf(
                    mapOf(
                        "issueKey" to "KEY-2",
                        "summary" to "this is the summary for second issue",
                        "type" to "Story"
                    ),
                    mapOf(
                        "issueKey" to "KEY-3",
                        "summary" to "this is the summary for third issue",
                        "type" to "Story"
                    )
                )
            )
        })

        val property = getProperty("testJiraVersion") as JiraVersionProperty

        assertEquals("testJiraVersion", property.key)
        assertEquals(
            mapOf(
                "Bug" to listOf(
                    mapOf(
                        "issueKey" to "KEY-1",
                        "summary" to "this is the summary for first issue",
                        "type" to "Bug"
                    )
                ),
                "Story" to listOf(
                    mapOf(
                        "issueKey" to "KEY-2",
                        "summary" to "this is the summary for second issue",
                        "type" to "Story"
                    ),
                    mapOf(
                        "issueKey" to "KEY-3",
                        "summary" to "this is the summary for third issue",
                        "type" to "Story"
                    )
                )
            ), property.value
        )
    }
}