package io.mths.kava.processor.generator.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName

/**
 * Parameterizes the receiver only in case [generics] is not empty.
 * Unlike [parameterizedBy], this does not throw an exception if [generics] is empty.
 */
fun ClassName.mayParameterizedBy(
    generics: Collection<TypeVariableName>
): TypeName =
    if (generics.isNotEmpty())
        parameterizedBy(generics.toList())
    else
        this