package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.*
import com.namics.oss.gradle.notification.utils.getProperty
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

/**
 * JiraVersionCollectorTest.
 *
 * @author rgsell, Namics AG
 * @since 05.08.20 17:52
 */
@Disabled("for manual testing")
internal class JiraVersionCollectorTest : AbstractMockHttpMockServer() {

    @Test
    fun collect() {
        val authHeader = basicAuthHeader(
            getTestProperty("collector.jiraversion.user"),
            getTestProperty("collector.jiraversion.password")
        )
        val collector = JiraVersionCollector(
            host = getTestProperty("collector.jiraversion.host"),
            version = getTestProperty("collector.jiraversion.version"),
            authHeader = authHeader,
            overwrite = true
        )
        collector.collect()
    }

    @Test
    fun extractRevision() {
        createMockServer()
        JiraVersionCollector(
            propertyKey = "jiraVersion",
            host = protocol + host + ":" + port,
            queryString = endpoint,
            authHeader = basicAuthHeader("info", "password"),
            version = "123",
            overwrite = true
        ).collect()
        val property = getProperty("jiraVersion") as Property.JiraVersionProperty
        assertEquals("jiraVersion", property.key)
        assertEquals(
            mapOf(
                "Bug" to listOf(
                    mapOf(
                        "issueKey" to "KEY-1",
                        "summary" to "summary 1",
                        "type" to "Bug"
                    ),
                    mapOf(
                        "issueKey" to "KEY-2",
                        "summary" to "summary 2",
                        "type" to "Bug"
                    )
                ),
                "Story" to listOf(
                    mapOf(
                        "issueKey" to "KEY-3",
                        "summary" to "summary 3",
                        "type" to "Story"
                    )
                )
            ), property.value
        )
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
                            "issues": [
                                {
                                    "key": "KEY-1",
                                    "fields": {
                                        "summary": "summary 1",
                                        "issuetype": {
                                            "name": "Bug"
                                        }
                                    }
                                },
                                {
                                    "key": "KEY-2",
                                    "fields": {
                                        "summary": "summary 2",
                                        "issuetype": {
                                            "name": "Bug"
                                        }
                                    }
                                },
                                {
                                    "key": "KEY-3",
                                    "fields": {
                                        "summary": "summary 3",
                                        "issuetype": {
                                            "name": "Story"
                                        }
                                    }
                                }
                            ]
                        }"""
                )
        )
    }
}