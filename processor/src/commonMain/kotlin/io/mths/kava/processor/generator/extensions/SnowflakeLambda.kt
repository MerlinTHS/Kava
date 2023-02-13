package io.mths.kava.processor.generator.extensions

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.mths.kava.ValidationScope
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo

@OptIn(ExperimentalKotlinPoetApi::class)
fun ExtensionInfo.snowflakeLambda(): PropertySpec {
    val scope = ValidationScope::class.asClassName()

    return PropertySpec
        .builder(name = "`*`", type)
        .receiver(
            LambdaTypeName.get(null, emptyList(), wrapperType)
        )
        .addTypeVariables(scopeTypes)
        .contextReceivers(scope.parameterizedBy(TypeVariableName("*")))
        .getter(
            FunSpec
                .getterBuilder()
                .addCode("""
                return with(this@%T) {
                    this@`*`().`*`
                }
            """.trimIndent(), scope
                ).build()
        ).build()
}