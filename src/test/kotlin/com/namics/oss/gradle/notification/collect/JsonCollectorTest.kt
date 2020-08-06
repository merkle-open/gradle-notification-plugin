package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.AbstractMockHttpMockServer
import com.namics.oss.gradle.notification.Property.StringProperty
import com.namics.oss.gradle.notification.basicAuthHeader
import com.namics.oss.gradle.notification.collect.JsonCollector
import com.namics.oss.gradle.notification.utils.getProperty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

/**
 * JsonCollectorTest.
 *
 * @author rgsell, Namics AG
 * @since 09.04.20 13:09
 */
@Disabled("for manual testing")
internal class JsonCollectorTest : AbstractMockHttpMockServer() {

    @Test
    fun extractRevision() {
        createMockServer()
        JsonCollector(
            propertyKey = "oldRevision",
            uri = protocol + host + ":" + port + endpoint,
            jsonPath = "git.commit.id.full",
            authHeader = basicAuthHeader("info", "password"),
            overwrite = true
        ).collect()
        val property = getProperty("oldRevision") as StringProperty
        assertEquals("oldRevision", property.key)
        assertEquals("e8e249ae15ea4b0d75e262c79933107a9d534452", property.value)
    }

    @Test
    fun extractVersion() {
        createMockServer()
        JsonCollector(
            propertyKey = "oldVersion",
            uri = protocol + host + ":" + port + endpoint,
            jsonPath = "build.version",
            authHeader = basicAuthHeader("info", "password"),
            overwrite = true
        ).collect()
        val property = getProperty("oldVersion") as StringProperty
        assertEquals("oldVersion", property.key)
        assertEquals("8.4.0-SNAPSHOT", property.value)
    }

    fun createMockServer() {
        MockServerClient(host, Integer.valueOf(port)).`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath(endpoint)
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(
                    """{
                            "git": {
                                "commit": {
                                    "id": { 
                                        "full": "e8e249ae15ea4b0d75e262c79933107a9d534452" 
                                    }
                                }
                            },
                            "build": {
                                "version": "8.4.0-SNAPSHOT"
                            }
                        }"""
                )
        )
    }
}