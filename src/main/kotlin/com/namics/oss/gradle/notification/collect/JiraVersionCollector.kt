package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Http
import com.namics.oss.gradle.notification.JiraIssue
import com.namics.oss.gradle.notification.Property
import com.namics.oss.gradle.notification.Property.JiraVersionProperty
import com.namics.oss.gradle.notification.utils.getPathElement
import com.namics.oss.gradle.notification.utils.getPathString

class JiraVersionCollector(
    override val propertyKey: String = "jiraVersion",
    val host: String,
    val version: String,
    val authHeader: String? = null,
    val queryString: String = "/rest/api/2/search?jql=fixVersion=$version&fields=id,summary,issuetype",
    val issuesPath: String = "issues",
    val keyPath: String = "key",
    val summaryPath: String = "fields.summary",
    val typePath: String = "fields.issuetype.name",
    override val overwrite: Boolean = false
) : Collector {

    override fun collectProperty(): Property {
        val uri = host + queryString
        val jsonElement = Http(authHeader).getJson(uri)
        val jsonIssues = jsonElement.jsonObject.getPathElement(issuesPath)
        val jiraIssues = jsonIssues?.jsonArray?.map {
            JiraIssue(
                key = it.jsonObject.getPathString(keyPath) ?: "",
                summary = it.jsonObject.getPathString(summaryPath) ?: "",
                type = it.jsonObject.getPathString(typePath) ?: ""
            )
        }?.groupBy { it.type }?.toMap()
        return JiraVersionProperty(key = propertyKey, value = jiraIssues ?: emptyMap())
    }
}