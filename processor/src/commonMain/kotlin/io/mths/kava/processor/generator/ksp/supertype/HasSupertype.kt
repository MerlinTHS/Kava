package io.mths.kava.processor.generator.ksp.supertype

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlin.reflect.KClass

fun KSClassDeclaration.hasSupertype(
    supertype: KClass<*>
): Boolean =
    getAllSuperTypes()
        .any { it.isClass(supertype) }
