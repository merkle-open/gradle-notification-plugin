package com.namics.oss.gradle.notification

import com.namics.oss.gradle.notification.Property.ListProperty
import com.namics.oss.gradle.notification.Property.StringProperty

/**
 * com.namics.oss.gradle.notification.Model.
 *
 * @author rgsell, Namics AG
 * @since 30.03.20 16:18
 */
class Model {
    var content = mutableMapOf<String, Any>()

    fun addProperty(property: Property) {
        content.put(property.key, property.getValue())
    }

    fun addAllProperties(properties: List<Property>){
        properties.forEach(this::addProperty)
    }

    fun property(init: StringProperty.() -> Unit) = addProperty(
        StringProperty().apply(init))

    fun listProperty(init: ListProperty.() -> Unit) = addProperty(
        ListProperty().apply(init))
}

fun model(init: Model.() -> Unit): Model = Model()
    .apply(init)