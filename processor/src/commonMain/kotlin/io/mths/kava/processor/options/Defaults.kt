package io.mths.kava.processor.options

val KavaOptions.Companion.Defaults: KavaOptions
    get() = KavaOptions(
        separateFiles = true,
        inferPackage = true,
        generatedPackage = "io.mths.kava.custom.validator",
        generatedFileName = "GeneratedKavaValidatorExtensions",
        generatedFileExtension = "Extensions"
    )