package io.mths.kava.processor.generator.extensions.info

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName

data class ExtensionInfo(
    val type: TypeName,
    val wrapperType: TypeName,
    val implementation: ClassName,
    val scopeTypes: Collection<TypeVariableName>
) {
    companion object
}