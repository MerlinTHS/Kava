package io.mths.kava.processor.generator.poet

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec

fun FileSpec.Builder.addProperties(
    vararg properties: PropertySpec
) {
    for (property in properties) {
        addProperty(property)
    }
}