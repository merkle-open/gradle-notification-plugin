package com.namics.oss.gradle.notification.collect

import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.listProperty
import com.namics.oss.gradle.notification.utils.getProperty
import com.namics.oss.gradle.notification.utils.saveProperty
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * TicketCollectorTest.
 *
 * @author rgsell, Namics AG
 * @since 20.04.20 17:14
 */
internal class TicketCollectorTest {

    @Test
    fun collect() {
        saveProperty(listProperty {
            key = "changes"
            value = listOf(
                "Reto Gsell: Merge branch 'feature/TICKET-1234-test-branch' into 'develop'",
                "Reto Gsell: Merge branch 'bugfix/TICKET-3214-NPEs' into 'develop'",
                "Max Muster: TICKET-1111 do some implementation",
                "Max Muster: TICKET-2222 do some other implementation",
                "Max Muster: TICKET-2222 polish",
                "Reto Gsell: TICKET-1234 fix test",
                "Max Muster: QA: cleanup unused code"
            )
        })

        TicketCollector(ticketPattern = "TICKET-[0-9]*").collect()
        val tickets = getProperty("issues") as ListProperty
        println(tickets.value.size)
        assertThat(tickets.value, hasSize(4))
        assertThat(
            tickets.value, allOf(
                hasItem("TICKET-1234"),
                hasItem("TICKET-1111"),
                hasItem("TICKET-2222"),
                hasItem("TICKET-3214")
            )
        )
    }
}