package com.namics.oss.gradle.notification

/**
 * DefaultTemplates.
 *
 * @author rgsell, Namics AG
 * @since 21.04.20 10:56
 */
class DefaultTemplates {
    companion object {
        const val CHAT_START = "templates/chat-start.mustache"
        const val CHAT_DONE = "templates/chat-done.mustache"
        const val SLACK_START ="templates/slack-start.mustache"
        const val SLACK_DONE = "templates/slack-done.mustache"
        const val MAIL_START = "templates/mail-start.mustache"
        const val MAIL_DONE = "templates/mail-done.mustache"
        const val NEW_RELIC_CHANGELOG ="templates/newrelic-changelog.mustache"
        const val NEW_RELIC_DESCRIPTION = "templates/newrelic-description.mustache"
    }
}