package com.namics.oss.gradle.notification.collect.rest

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
    fun extract() {
        createMockServer()
        JsonCollector(
            propertyKey = "version",
            uri = protocol + host + ":" + port + endpoint,
            jsonPath = "git.commit.id.full",
            authHeader = basicAuthHeader("info", "password")
        ).collect()
        val property = getProperty("version") as StringProperty
        assertEquals("version", property.key)
        assertEquals("e8e249ae15ea4b0d75e262c79933107a9d534452", property.value)
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
                            }
                        }"""
                )
        )
    }
}