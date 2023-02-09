package io.mths.kava.processor.generator.poet

import com.squareup.kotlinpoet.*

fun scopeBuilder(
    name: String,
    returns: TypeName,
    lambdaReceiver: TypeName,
    validator: ClassName,
    validate: MemberName,
    genericType: TypeName,
    scopeTypes: Collection<TypeVariableName>
): FunSpec {
    val paramName = "block"

    return FunSpec.builder(name)
        .addTypeVariables(scopeTypes)
        .addParameter(
            name = paramName,
            type = LambdaTypeName.get(lambdaReceiver, emptyList(), genericType)
        )
        .returns(returns)
        .addStatement("return %M(%T(), $paramName)", validate, validator)
        .build()
}