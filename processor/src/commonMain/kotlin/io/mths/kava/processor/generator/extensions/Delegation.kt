package io.mths.kava.processor.generator.extensions

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.mths.kava.ValidationScope
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo
import kotlin.reflect.KProperty

@OptIn(ExperimentalKotlinPoetApi::class)
fun ExtensionInfo.delegation(): FunSpec {
    val scope = ValidationScope::class.asClassName()

    return FunSpec
        .builder("getValue")
        .contextReceivers(scope.parameterizedBy(TypeVariableName("*")))
        .addModifiers(KModifier.OPERATOR)
        .addTypeVariables(scopeTypes)
        .receiver(wrapperType)
        .addParameter("thisRef", Any::class.asTypeName().copy(nullable = true))
        .addParameter("property", KProperty::class.asClassName().parameterizedBy(TypeVariableName("*")))
        .addCode(
            """
                return with(this@%T) {
                    this@getValue.`*`
                }
            """.trimIndent(), scope
        )
        .returns(type)
        .build()
}