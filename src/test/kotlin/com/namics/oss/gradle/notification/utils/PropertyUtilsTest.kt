package com.namics.oss.gradle.notification.utils

import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.listProperty
import com.namics.oss.gradle.notification.property
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
}