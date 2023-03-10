package io.mths.kava.processor.generator.extensions

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.mths.kava.ValidationScope
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo
import io.mths.kava.processor.generator.poet.mayParameterizedBy

@OptIn(ExperimentalKotlinPoetApi::class)
fun ExtensionInfo.snowflake(): PropertySpec {
    val scope = ValidationScope::class.asClassName()
    val parameterizedImpl = implementation.mayParameterizedBy(scopeTypes)

    return PropertySpec
        .builder("`*`", type)
        .receiver(wrapperType)
        .addTypeVariables(scopeTypes)
        .contextReceivers(scope.parameterizedBy(TypeVariableName("*")))
        .getter(
            FunSpec
                .getterBuilder()
                .addCode("""
                    return with(this@%1T) {
                        %2T().run {
                            this@%1T.validate(this@`*`)
                        }
                    }
                """.trimIndent(), scope, parameterizedImpl
                ).build()
        )
        .build()
}