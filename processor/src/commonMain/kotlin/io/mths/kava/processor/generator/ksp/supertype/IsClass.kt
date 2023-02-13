package io.mths.kava.processor.generator.ksp.supertype

import com.google.devtools.ksp.symbol.KSType
import kotlin.reflect.KClass

fun KSType.isClass(clazz: KClass<*>): Boolean {
    val actualName = declaration.qualifiedName?.asString()
        ?: return false
    val expectedName = clazz.qualifiedName
        ?: return false

    return (actualName == expectedName)
}