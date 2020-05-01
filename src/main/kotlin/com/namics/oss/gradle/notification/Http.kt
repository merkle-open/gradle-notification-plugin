package com.namics.oss.gradle.notification

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElement
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets.ISO_8859_1
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.time.Duration
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Http Client connections.
 *
 * @author rgsell, Namics AG
 * @since 08.04.20 13:20
 */
class Http(val authHeader: String? = null) {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? = null
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    })

    val httpClient: HttpClient

    init {
        val httpClientBuilder = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .sslContext(run {
                val sslContext: SSLContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, SecureRandom())
                sslContext
            })

        httpClient = httpClientBuilder.build()
    }

    fun getJson(uri: String, headers: Map<String, String> = emptyMap()): JsonElement {
        val requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("accept", "application/json")

        if(authHeader!=null ){
            requestBuilder.header("Authorization", authHeader)
        }
        headers.forEach { entry -> requestBuilder.header(entry.key, entry.value) }

        val request = requestBuilder.build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val json = Json(JsonConfiguration.Stable)
        return json.parseJson(response.body())
    }

    fun postJson(payload: String, uri: String, headers: Map<String, String> = emptyMap()): HttpResponse<String> {
        val requestBuilder = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(payload))
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")

        if(authHeader!=null ){
            requestBuilder.header("Authorization", authHeader)
        }

        headers.forEach { entry -> requestBuilder.header(entry.key, entry.value) }

        val request = requestBuilder.build()
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }
}

fun basicAuthHeader(username: String, password: String): String {
    val auth = username+":"+password
    val encodedAuth = Base64.getEncoder().encode(auth.toByteArray(ISO_8859_1))
    return "Basic "+ String(encodedAuth)
}