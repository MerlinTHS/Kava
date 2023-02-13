package io.mths.kava.processor.generator.ksp.annotation

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration

@OptIn(KspExperimental::class)
inline fun <reified Type: Annotation> KSClassDeclaration.getAnnotation() =
    getAnnotationsByType(Type::class)
        .firstOrNull()
        ?: throw AnnotationNotFound(
            annotation = Type::class,
            annotatedClass = qualifiedName?.asString() ?: "Unknown class"
        )