group = "com.namics.oss.gradle.notification"
description = "Gradle plugin for notifications"

plugins {
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.serialization") version "1.3.70"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.11.0"
    `java-gradle-plugin`
    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc01"
    id("com.github.ben-manes.versions") version "0.27.0"
}


repositories {
    mavenCentral()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

gradlePlugin {
    plugins {
        register("com.namics.oss.gradle.notification-plugin") {
            id = "com.namics.oss.gradle.notification-plugin"
            implementationClass = "com.namics.oss.gradle.notification.NotificationPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/namics/gradle-notification-plugin"
    vcsUrl = "https://github.com/namics/gradle-notification-plugin"
    description = project.description
    tags = listOf("version", "release", "vcs")
    (plugins) {
        "com.namics.oss.gradle.notification-plugin" {
            // id is captured from java-gradle-plugin configuration
            displayName = "Gradle Notification Plugin"
            description = "Gradle plugin to send notifications from gradle"
            tags = listOf("version", "release", "vcs")
            version = project.version.toString()
        }
    }
    mavenCoordinates {
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
    }
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("com.github.spullara.mustache.java:compiler:0.9.6")
    implementation("javax.mail:mail:1.4.7")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.mock-server:mockserver-netty:5.10.0")
    testImplementation("org.mock-server:mockserver-client-java:5.10.0")
}