package io.mths.kava.processor.options

import kotlin.reflect.KProperty

internal const val prefix = "kava"

internal val KProperty<*>.qualifiedOptionName: String
    get() = "$prefix.${name.lowercase()}"

internal fun Set<String>.findQualifiedOption(property: KProperty<*>) =
    find { it.lowercase() == property.qualifiedOptionName }