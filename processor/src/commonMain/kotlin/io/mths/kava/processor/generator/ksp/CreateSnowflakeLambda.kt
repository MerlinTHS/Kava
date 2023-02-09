package io.mths.kava.processor.generator.ksp

import io.mths.kava.processor.generator.poet.snowflakeLambda
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FunSpec

fun KSClassDeclaration.createSnowflakeLambda(): FunSpec {
    return snowflakeLambda()
}