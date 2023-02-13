package io.mths.kava.processor.generator.ksp.annotation

import kotlin.reflect.KClass

class AnnotationNotFound(
    annotation: KClass<out Annotation>,
    annotatedClass: String
) : Exception(
    "Annotation [${annotation.qualifiedName}] not found in $annotatedClass!"
)