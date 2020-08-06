package com.namics.oss.gradle.notification

import org.junit.jupiter.api.AfterAll
import org.mockserver.integration.ClientAndServer

/**
 * AbstractMockHttpMockServer.
 *
 * @author rgsell, Namics AG
 * @since 24.04.20 11:12
 */
abstract class AbstractMockHttpMockServer {
    companion object {
        val protocol = getTestProperty("collector.json.protocol")
        val host = getTestProperty("collector.json.host")
        val port = getTestProperty("collector.json.port")
        val endpoint = getTestProperty("collector.json.endpoint")

        var mockServer = ClientAndServer.startClientAndServer(Integer.valueOf(port))

        @AfterAll
        @JvmStatic
        fun stopServer() {
            mockServer.stop()
        }
    }
}