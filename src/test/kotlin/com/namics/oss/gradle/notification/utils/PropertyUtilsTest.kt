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
                    JiraIssue(
                        key = "KEY-1",
                        summary = "this is the summary for first issue",
                        type = "Bug"
                    )
                ),
                "Story" to listOf(
                    JiraIssue(
                        key = "KEY-2",
                        summary = "this is the summary for second issue",
                        type = "Story"
                    ),
                    JiraIssue(
                        key = "KEY-3",
                        summary = "this is the summary for third issue",
                        type = "Story"
                    )
                )
            )
        })

        val property = getProperty("testJiraVersion") as JiraVersionProperty

        assertEquals("testJiraVersion", property.key)
        assertEquals(
            mapOf(
                "Bug" to listOf(
                    JiraIssue(
                        key = "KEY-1",
                        summary = "this is the summary for first issue",
                        type = "Bug"
                    )
                ),
                "Story" to listOf(
                    JiraIssue(
                        key = "KEY-2",
                        summary = "this is the summary for second issue",
                        type = "Story"
                    ),
                    JiraIssue(
                        key = "KEY-3",
                        summary = "this is the summary for third issue",
                        type = "Story"
                    )
                )
            ), property.value
        )
    }
}