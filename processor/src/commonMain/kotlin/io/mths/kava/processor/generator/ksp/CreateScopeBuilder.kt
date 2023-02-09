package io.mths.kava.processor.generator.ksp

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.*
import io.mths.kava.processor.generator.poet.scopeBuilder
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.*
import io.mths.kava.GenerateExtensions
import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.processor.util.getQualifiedName

fun KSClassDeclaration.createScopeBuilder(): FunSpec {
    val resolver = typeParameters.toTypeParameterResolver()
    val validator = this.getAllSuperTypes().find {
        getQualifiedName(Validator::class) == it.declaration.qualifiedName?.asString()
    } ?: throw IllegalStateException("[${simpleName.asString()}] is not a subclass of Validator!")

    val type = validator.findGenericType(resolver)
    val wrapperType = validator.findGenericWrapperType(resolver)

    val kavaAnnotation = annotations
        .find { it.shortName.asString() == (GenerateExtensions::class.simpleName!!) }!!
    val scopeName = kavaAnnotation.arguments[0].value as String

    return scopeBuilder(
        name = scopeName,
        genericType = type,
        scopeTypes = resolver.parametersMap.values,
        returns = wrapperType,
        validator = toClassName(),
        validate = MemberName("io.mths.kava.scope", "validate"),
        lambdaReceiver = ValidationScope::class.asClassName().parameterizedBy(type)
    )
}

internal fun KSType.findGenericType(resolver: TypeParameterResolver): TypeName =
    arguments.typeNameAt(0, resolver)

internal fun KSType.findGenericWrapperType(resolver: TypeParameterResolver): TypeName =
    arguments.typeNameAt(1, resolver)

internal fun List<KSTypeArgument>.typeNameAt(
    index: Int,
    resolver: TypeParameterResolver
): TypeName =
    get(index)
        .toTypeName(resolver)