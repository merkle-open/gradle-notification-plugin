package com.namics.oss.gradle.notification

import kotlinx.serialization.Serializable

/**
 * JiraIssue.
 *
 * @author rgsell, Namics AG
 * @since 05.08.20 16:45
 */
@Serializable
data class JiraIssue(
    var key: String = "",
    var summary: String = "",
    var type: String = ""
)