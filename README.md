# Gradle notification plugin
## What is `gradle-notification-plugin`
The `gradle-notification-plugin` is a supportive plugin to collect and send information on multiple channels.
## Getting Started
Add plugin in gradle Project.

Kotlin
```kotlin
plugins {
  id("com.namics.oss.gradle.notification-plugin") version "0.3.0-0"
}
```
Groovy
```Groovy
plugins {
  id "com.namics.oss.gradle.notification-plugin" version "0.3.0-0"
}
```
## How does it work?
In the core, the plugin collects properties, serializes them into files and sends messages to defined channels. Custom properties and templates can be used for customizable messages.
### Collectors
Collectors collect information and stores them to files. The plugin implements following collectors: (`StringCollector`, `ListCollector`, `JsonCollector`, `JsonCollector`, `GitHistoryCollector`, `TicketCollector`). If you need more flexibility you can implement your own Collector by implementing the Interface.
#### StringCollector
Stores a property (value = `String`).
```kotlin
property("propertyKey", "property value")
```
#### ListCollector
Stores a list property (value = `List<String>`).
```kotlin
listProperty(
    "propertyKey",
    listOf(
        "first list value",
        "second list value",
        "third list value",
        "..."
    )
)
```
#### JsonCollector
Collects a value from a json enabled endpoint.

Stores a property (value = `String`).
```kotlin
JsonCollector(
    propertyKey = "oldRevision",
    uri = "https://github.com/some.json",
    jsonPath = "json.path.to.value",
    authHeader = basicAuthHeader("username", "password") // this String will be added to Authorization header, leave empty if no authorization needed
)
```
#### GitHistoryCollector
Collects Git log, this collector depends on `git` available on the executing environment. As well there needs to be a revision already defined in the properties to get logs from (default propertyKey for old revision is `oldRevision`, can be overwritten by property oldRevisionPropertyKey).

Stores a list property (value = `List<String>`).
```kotlin
GitHistoryCollector(
    rootPath = "/path/to/your/git/directory"
)
```
#### TicketCollector
Collects Ticket numbers from a list property by a regex pattern. Depends on an existing property to work on (default value for history list property is `changes`)

Stores a list property (value = `List<String>`).
```kotlin
TicketCollector(
    propertyKey = "issues",
    ticketPattern = "TICKET-[0-9]*" //regex pattern
)
```
### Senders
A Sender sends notifications to different channels with use of stored properties and mustache templating. Every Sender allows a custom template for messages. The plugin implements following senders: (`ChatSender`, `Slacksender`, `TeamsSender`, `MailSender`, `NewRelicSender`). If you need more flexibility you can implement your own Sender by implementing the Sender Interface.
#### ChatSender
Sends notifications to Google Chat.
```kotlin
ChatSender(
    webhook = "https://chat.googleapis.com/v1/...",
    threadKey = "chatThreadKey"
)
```
#### SlackSender
Send notifications to Slack Channel.
```kotlin
SlackSender(
    webhook = slackWebhook,
    channel = "#gradle-notification-plugin"
)
```
#### TeamsSender
Send notifications to Microsoft Teams Channel.
```kotlin
SlackSender(
    webhook = teamsWebhook
)
```
#### MailSender
Send notifcation to Email.
```kotlin
MailSender(
    from = "info@github.com",
    to = "info@github.com, info2@github.com",
    subject = "Mail Subject",
    smtpHost = "smtp.gmail.com"
)
```
#### NewRelicSender
Creates Deployment in New Relic.
```kotlin
NewRelicSender(
    webhook = "https://api.newrelic.com/v2/applications/123123123/deployments.json",
    apiKey = "apikey123123123123123",
    user = "info@github.com",
    revision = "revision_number"
)
```
#### ConsoleSender
Print notification to console.
```kotlin
ConsoleSender()
```
### Templating
The Plugin uses mustache templates to send notification. Every sender allows a custom template which overwrites the default template.

Default templates can be found in resources [src/main/resources/templates](src/main/resources/templates)

### Example
This example shows how to collect properties and send a message to the console.

`SenderTask.kt`
```kotlin
...
fun sendMessage() {
    notification {
        collectors(
            StringCollector("message", "Hello World!"),
            listProperty(
                "collectors",
                listOf(
                    "GitHistoryCollector",
                    "JsonCollector",
                    "ListCollector",
                    "StringCollector",
                    "TicketCollector",
                    "... or implement your own com.namics.oss.gradle.notification.collect.Collector"
                )
            ),
            listProperty(
                "senders",
                listOf(
                    "ChatSender",
                    "ConsoleSender",
                    "MailSender",
                    "NewRelicSender",
                    "SlackSender",
                    "... or implement your own com.namics.oss.gradle.notification.send.Sender"
                )
            )
        )
        senders(
            ConsoleSender("templates/console.mustache")
        )
    }.collect().send()
}
```
`templates/console.mustache`
```mustache
Message has been sent successfully to console .
message: {{message}}

Available collectors:
{{#collectors}}
- {{value}}
{{/collectors}}

Available senders:
{{#senders}}
- {{value}}
{{/senders}}

See README.md for instructions.
```