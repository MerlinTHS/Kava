package io.mths.kava.processor.generator.extensions

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.mths.kava.ValidationScope
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo

fun ExtensionInfo.scopeBuilder(
    scopeName: String
): FunSpec {
    val paramName = "block"
    val validate = MemberName("io.mths.kava.scope", "validate")
    val lambdaReceiver = ValidationScope::class.asClassName()

    return FunSpec.builder(scopeName)
        .addTypeVariables(scopeTypes)
        .addParameter(
            name = paramName,
            type = LambdaTypeName.get(lambdaReceiver.parameterizedBy(TypeVariableName("*")), emptyList(), type),
        )
        .returns(wrapperType)
        .addStatement("return %M(%T(), $paramName)", validate, implementation)
        .build()
}