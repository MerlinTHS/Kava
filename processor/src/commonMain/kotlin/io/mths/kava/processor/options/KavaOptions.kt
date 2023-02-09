package io.mths.kava.processor.options

data class KavaOptions(
    val separateFiles: Boolean,
    val inferPackage: Boolean,
    val generatedPackage: String,
    val generatedFileName: String,
    val generatedFileExtension: String
) {
    companion object
}