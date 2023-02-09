package io.mths.kava.processor.util

import kotlin.reflect.KClass

fun getQualifiedName(clazz: KClass<*>): String {
    return clazz.qualifiedName
        ?: throw IllegalStateException("Qualified name for not found")
}