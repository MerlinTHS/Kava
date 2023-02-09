package io.mths.kava.processor.options

import kotlin.reflect.KProperty

fun KavaOptions.Companion.of(
    options: Map<String, String>
) = KavaOptions(
    separateFiles =
        options[KavaOptions::separateFiles].toBoolean(),
    inferPackage =
        options[KavaOptions::inferPackage].toBoolean(),
    generatedPackage =
        options[KavaOptions::generatedPackage] ?: Defaults.generatedPackage,
    generatedFileName =
        options[KavaOptions::generatedFileName] ?: Defaults.generatedFileName,
    generatedFileExtension =
        options[KavaOptions::generatedFileExtension] ?: Defaults.generatedFileExtension
)

internal operator fun Map<String, String>.get(
    optionProperty: KProperty<*>
): String? {
    val key = keys.findQualifiedOption(optionProperty)
        ?: return null

    return get(key)
}