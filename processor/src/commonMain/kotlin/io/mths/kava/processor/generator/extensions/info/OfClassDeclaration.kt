package io.mths.kava.processor.generator.extensions.info

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.TypeParameterResolver
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import io.mths.kava.processor.generator.ksp.supertype.findValidatorSupertype

fun ExtensionInfo.Companion.of(declaration: KSClassDeclaration): ExtensionInfo {
    val resolver = declaration.typeParameters.toTypeParameterResolver()
    val validator = declaration.findValidatorSupertype()

    return ExtensionInfo(
        type = validator.findGenericType(resolver),
        wrapperType = validator.findGenericWrapperType(resolver),
        implementation = declaration.toClassName(),
        scopeTypes = resolver.parametersMap.values
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
    get(index).toTypeName(resolver)