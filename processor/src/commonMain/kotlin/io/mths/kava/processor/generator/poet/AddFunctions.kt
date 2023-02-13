package io.mths.kava.processor.generator.poet

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

fun FileSpec.Builder.addFunctions(
    vararg functions: FunSpec
) {
    for (function in functions) {
        addFunction(function)
    }
}