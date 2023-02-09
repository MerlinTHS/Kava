package io.mths.kava.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.mths.kava.processor.options.KavaOptions
import io.mths.kava.processor.options.of

class KavaProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = with(environment) {
        KavaProcessor(
            LOG = logger,
            options = KavaOptions.of(options),
            codeGenerator = codeGenerator
        )
    }
}