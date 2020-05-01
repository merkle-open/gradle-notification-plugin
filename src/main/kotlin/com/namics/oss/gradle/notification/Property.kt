package com.namics.oss.gradle.notification

import com.github.mustachejava.util.DecoratedCollection
import com.namics.oss.gradle.notification.collect.ListCollector
import com.namics.oss.gradle.notification.collect.StringCollector
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Properties used in Notifications and model.
 *
 * @author rgsell, Namics AG
 * @since 20.04.20 18:38
 */
@Serializable
sealed class Property {
    abstract val key: String
    abstract fun getValue(): Any

    @Serializable
    @SerialName("StringProperty")
    data class StringProperty(
        override var key: String = "",
        var value: String = ""
    ) : Property() {
        override fun getValue(): Any {
            return value
        }

        override fun toString(): String {
            return "Property(key='$key', value='$value')"
        }
    }
    @Serializable
    @SerialName("ListProperty")
    data class ListProperty (
        override var key: String = "",
        var value: List<String> = emptyList()
    ) : Property() {
        override fun getValue(): Any {
            return DecoratedCollection(value)
        }

        override fun toString(): String {
            return "Property(key='$key', value='$value')"
        }
    }
}
fun property(init: Property.StringProperty.() -> Unit): Property.StringProperty {
    return Property.StringProperty().apply(init)
}

fun listProperty(init: Property.ListProperty.() -> Unit): Property.ListProperty {
    return Property.ListProperty().apply(init)
}
