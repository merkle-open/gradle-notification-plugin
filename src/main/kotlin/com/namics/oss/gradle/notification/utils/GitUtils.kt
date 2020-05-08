package com.namics.oss.gradle.notification.utils

import org.gradle.api.GradleException
import org.gradle.api.logging.Logger
import java.io.File
import java.io.IOException
import java.lang.ProcessBuilder.Redirect.PIPE
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

/**
 * Git.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 11:02
 */
class GitUtils(
    val rootPath: File,
    val logger: Logger,
    val timoutSeconds: Long = 120L
) {

    fun log(newRevision: String, oldRevision: String, limit: Int) = perform("log","$oldRevision..$newRevision","-$limit", "--pretty='%an: %s'")

    private fun perform(vararg arguments: String): Stream<String> {
        try {
            val process = ProcessBuilder("git", *arguments)
                .directory(rootPath)
                .redirectOutput(PIPE)
                .redirectError(PIPE)
                .start()

            process.waitFor(timoutSeconds, TimeUnit.SECONDS)
            val errors = ArrayList<String>()
            process.errorStream.bufferedReader().lines().forEach {
                logger.error("GIT: {}", it)
                errors.add(it)
            }

            val exitValue = process.exitValue()
            if (exitValue != 0)
                throw GradleException(
                    "exit $exitValue; \nCommand 'git ${arguments.joinToString(separator = " ")}' failed!\n${errors.joinToString(
                        "\n"
                    )}"
                )

            return process.inputStream.bufferedReader().lines().map { it.substring(1, it.length -1) }
        } catch (e: IOException) {
            throw GradleException("Failed to execute command 'git ${arguments.joinToString(separator = " ")}'", e)
        }
    }
}