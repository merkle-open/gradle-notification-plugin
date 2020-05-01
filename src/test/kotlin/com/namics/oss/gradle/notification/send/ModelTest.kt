package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.model
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

/**
 * ModelTest.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 14:26
 */
internal class ModelTest {

    @Test
    fun testMessageBuilder() {
        val model = model {
            property {
                key = "key1"
                value = "value1"
            }
            property {
                key = "key2"
                value = "value2"
            }
            property {
                key = "key2"
                value = "value3"
            }
        }

        assertThat(
            model.content as Map<String, Any>, allOf(
                aMapWithSize(2),
                hasEntry(equalTo("key1"), equalTo<Any>("value1")),
                hasEntry(equalTo("key2"), equalTo<Any>("value3"))
            )
        )
    }
}