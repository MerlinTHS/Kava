package io.mths.kava.processor.generator.ksp

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver

val Resolver.aggregatingDependencies: Dependencies
    get() = Dependencies(
        aggregating = true,
        sources = getAllFiles().toList().toTypedArray()
    )